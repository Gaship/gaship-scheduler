package shop.gaship.gashipscheduler.scheduler.gradeadvancement.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import shop.gaship.gashipscheduler.domain.membergrade.adapter.AdvancementAdapter;
import shop.gaship.gashipscheduler.domain.membergrade.dto.request.AdvancementMemberRequestDto;
import shop.gaship.gashipscheduler.domain.membergrade.dto.request.GradeHistoryAddRequestDto;
import shop.gaship.gashipscheduler.domain.membergrade.dto.request.RenewalMemberGradeRequestDto;
import shop.gaship.gashipscheduler.domain.membergrade.dto.response.MemberGradeResponseDto;
import shop.gaship.gashipscheduler.scheduler.gradeadvancement.entity.AdvancementTarget;
import shop.gaship.gashipscheduler.scheduler.gradeadvancement.exception.MemberGradeEvaluateException;

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
public class ProgressGradeAdvancementProcessor
        implements ItemProcessor<AdvancementTarget, RenewalMemberGradeRequestDto> {

    private final AdvancementAdapter advancementAdapter;

    private List<MemberGradeResponseDto> memberGradeList;

    @Override
    public RenewalMemberGradeRequestDto process(AdvancementTarget advancementTarget) {
        Long accumulatePurchaseAmount = advancementAdapter
                .getAccumulatePurchaseAmount(advancementTarget.getMemberNo(),
                        advancementTarget.getNextRenewalGradeDate());

        MemberGradeResponseDto evaluatedMemberGrade = evaluateMemberGrade(accumulatePurchaseAmount);

        AdvancementMemberRequestDto advancementMemberRequestDto =
                AdvancementMemberRequestDto.builder()
                        .memberNo(advancementTarget.getMemberNo())
                        .memberGradeNo(evaluatedMemberGrade.getNo())
                        .nextRenewalGradeDate(calculateNextRenewalDate(advancementTarget
                                .getNextRenewalGradeDate()))
                        .build();

        GradeHistoryAddRequestDto gradeHistoryAddRequestDto =
                GradeHistoryAddRequestDto.builder()
                        .memberNo(advancementTarget.getMemberNo())
                        .totalAmount(accumulatePurchaseAmount)
                        .gradeName(evaluatedMemberGrade.getName())
                        .at(advancementTarget.getNextRenewalGradeDate())
                        .build();

        return RenewalMemberGradeRequestDto.builder()
                .advancementMemberRequestDto(advancementMemberRequestDto)
                .gradeHistoryAddRequestDto(gradeHistoryAddRequestDto)
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
                .getRenewalPeriodStatusCode());

        return renewalDate.plusMonths(renewalPeriod);
    }
}
