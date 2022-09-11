package shop.gaship.scheduler.coupongenerationissue.scheduler.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeProcess;

/**
 * @author : 최겸준
 * @since 1.0
 */
@Slf4j
public class EmptyFileReadListner  {

    @AfterStep
    public ExitStatus afterStep(StepExecution stepExecution) {
        if (stepExecution.getReadCount() <= 0) {
            String memberGradeNo = stepExecution.getJobParameters().getString("memberGradeNo");
            log.error("{}번 회원 등급 번호에 대한 등급 또는 등급의 회원이 없습니다.", memberGradeNo);
            return ExitStatus.FAILED;
        }

        return stepExecution.getExitStatus();
    }
}
