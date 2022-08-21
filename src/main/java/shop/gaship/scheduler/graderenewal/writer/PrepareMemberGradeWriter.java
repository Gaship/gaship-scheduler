package shop.gaship.scheduler.graderenewal.writer;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import shop.gaship.scheduler.graderenewal.dto.MemberGradeResponseDto;

/**
 * 읽어온 회원등급 데이터를 저장공간에 저장하기 위한 Item Writer 구현체.
 *
 * @author : 김세미
 * @since 1.0
 */
@Component
@StepScope
@Slf4j
public class PrepareMemberGradeWriter implements ItemWriter<MemberGradeResponseDto> {
    private StepExecution stepExecution;

    /**
     * {@inheritDoc}
     * JdbcPagingItemReader 를 통해 읽어온 회원등급 종류에 대한 데이터를
     * 다음 step 과 공유하기 위해 stepExecution 에 저장합니다.
     */
    @Override
    public void write(List<? extends MemberGradeResponseDto> memberGradeList) {
        ExecutionContext executionContext = this.stepExecution.getExecutionContext();
        executionContext.put("memberGradeList", memberGradeList);
    }

    @BeforeStep
    public void saveStepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }
}
