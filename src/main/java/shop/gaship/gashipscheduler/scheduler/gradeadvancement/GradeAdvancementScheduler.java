package shop.gaship.gashipscheduler.scheduler.gradeadvancement;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import shop.gaship.gashipscheduler.scheduler.gradeadvancement.config.GradeAdvancementConfig;

/**
 * 회원승급 job scheduler.
 *
 * @author : 김세미
 * @since 1.0
 */
@RequiredArgsConstructor
@Component
public class GradeAdvancementScheduler {
    private final JobLauncher jobLauncher;
    private final GradeAdvancementConfig gradeAdvancementConfig;

    /**
     * 매일 밤 자정마다 실행되는 회원승급 job 실행 메서드.
     *
     * @throws Exception job 실행 관련 exception.
     * @author 김세미
     */
    @Scheduled(cron = "0 0 24 * * ?")
    public void doGradeAdvancement() throws Exception {
        Job job = gradeAdvancementConfig.advanceJob();
        JobParameters jobParameters = new JobParameters();
        jobLauncher.run(job, jobParameters);
    }
}
