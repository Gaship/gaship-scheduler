package shop.gaship.scheduler.gradeadvancement.scheduler.writer;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import shop.gaship.scheduler.gradeadvancement.domain.membergrade.dto.response.MemberGradeResponseDto;

/**
 * 읽어온 회원등급 데이터를 저장공간에 저장하기 위한 Item Writer 구현체.
 *
 * @author : 김세미
 * @since 1.0
 */
@Component
@StepScope
@Slf4j
public class PrepareMemberGradeWriter implements ItemWriter<List<MemberGradeResponseDto>> {
    private StepExecution stepExecution;

    @Override
    public void write(List<? extends List<MemberGradeResponseDto>> memberGradeList) {
        log.debug("다음 step 에 넘어갈 회원등급 데이터 : {}", memberGradeList.toString());

        ExecutionContext executionContext = this.stepExecution.getExecutionContext();
        executionContext.put("memberGradeList", memberGradeList);
    }

    @BeforeStep
    public void saveStepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }
}
