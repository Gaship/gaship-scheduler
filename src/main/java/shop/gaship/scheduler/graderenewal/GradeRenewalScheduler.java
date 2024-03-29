package shop.gaship.scheduler.graderenewal;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import shop.gaship.scheduler.graderenewal.config.GradeRenewalJobConfig;
import shop.gaship.scheduler.graderenewal.exception.MemberGradeRenewalRunException;

/**
 * 회원 등급 갱신 job scheduler.
 *
 * @author : 김세미
 * @since 1.0
 */
@RequiredArgsConstructor
@Component
public class GradeRenewalScheduler {
    private final JobLauncher jobLauncher;
    private final GradeRenewalJobConfig gradeRenewalJobConfig;


    /**
     * 매일 밤 자정마다 실행되는 회원등급 갱신 job 실행 메서드.
     *
     * @throws MemberGradeRenewalRunException 회원승급 작업 실행시 발생할 수 있는 exception.
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void doGradeRenewal() {
        Job job = gradeRenewalJobConfig.renewalJob();
        JobParameters jobParameters = new JobParameters();

        try {
            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | JobRestartException e) {
            throw new MemberGradeRenewalRunException();
        }
    }
}
