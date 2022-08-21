package shop.gaship.scheduler.graderenewal.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import shop.gaship.scheduler.graderenewal.dto.MemberGradeResponseDto;
import shop.gaship.scheduler.graderenewal.dto.RenewalMemberGradeRequestDto;
import shop.gaship.scheduler.graderenewal.dto.RenewalTargetMemberDto;
import shop.gaship.scheduler.graderenewal.exception.MemberGradeEvaluateException;


/**
 * 승급 대상회원 데이터와 회원등급 데이터를 통해
 * 승급 데이터를 처리하는 Item Processor 구현체.
 *
 * @author : 김세미
 * @since 1.0
 * @see org.springframework.batch.item.ItemProcessor
 */
@Component
@RequiredArgsConstructor
@StepScope
public class ProgressGradeRenewalProcessor
        implements ItemProcessor<RenewalTargetMemberDto, RenewalMemberGradeRequestDto> {

    private List<MemberGradeResponseDto> memberGradeList;

    @Override
    public RenewalMemberGradeRequestDto process(RenewalTargetMemberDto renewalTargetMember) {

        if (Objects.isNull(renewalTargetMember.getAccumulateAmount())) {
            renewalTargetMember.setAccumulateAmount(0L);
        }

        MemberGradeResponseDto evaluatedMemberGrade = evaluateMemberGrade(
                renewalTargetMember.getAccumulateAmount());

        return RenewalMemberGradeRequestDto.builder()
                .memberNo(renewalTargetMember.getMemberNo())
                .totalAmount(renewalTargetMember.getAccumulateAmount())
                .gradeName(evaluatedMemberGrade.getName())
                .at(renewalTargetMember.getRenewalGradeDate())
                .memberGradeNo(evaluatedMemberGrade.getNo())
                .nextRenewalGradeDate(calculateNextRenewalDate(renewalTargetMember
                        .getRenewalGradeDate()))
                .build();
    }

    /**
     * PrepareMemberGrade step 에서 가공한 회원등급 데이터를
     * ProgressGradeAdvancement step 에서 사용하기 위해 step 시작 전에 동작하는 메서드.
     *
     * @param stepExecution 이전 Step 의 실행데이터 (StepExecution)
     */
    @BeforeStep
    public void retrieveMemberGradeData(StepExecution stepExecution) {
        ObjectMapper objectMapper = new ObjectMapper();
        JobExecution jobExecution = stepExecution.getJobExecution();

        this.memberGradeList = objectMapper
                .convertValue(jobExecution.getExecutionContext().get("memberGradeList"),
                objectMapper
                        .getTypeFactory()
                        .constructCollectionType(List.class, MemberGradeResponseDto.class));
    }

    private MemberGradeResponseDto evaluateMemberGrade(Long accumulateAmount) {
        Optional<MemberGradeResponseDto> memberGrade = this.memberGradeList
                .stream()
                .filter(item -> item.getAccumulateAmount().equals(accumulateAmount))
                .findFirst();

        if (memberGrade.isPresent()) {
            return memberGrade.get();
        }

        List<Long> amountList = this.memberGradeList
                .stream()
                .map(MemberGradeResponseDto::getAccumulateAmount)
                .collect(Collectors.toList());

        amountList.add(accumulateAmount);
        amountList.sort(null);

        int accumulateAmountIndex = amountList
                .indexOf(accumulateAmount);

        return this.memberGradeList
                .stream()
                .filter(memberGradeResponseDto ->
                        memberGradeResponseDto
                                .getAccumulateAmount()
                                .equals(amountList.get(accumulateAmountIndex - 1)))
                .findFirst()
                .orElseThrow(MemberGradeEvaluateException::new);
    }

    private LocalDate calculateNextRenewalDate(LocalDate renewalDate) {
        int renewalPeriod = Integer.parseInt(this.memberGradeList.get(0)
                .getRenewalPeriod());

        return renewalDate.plusMonths(renewalPeriod);
    }
}
