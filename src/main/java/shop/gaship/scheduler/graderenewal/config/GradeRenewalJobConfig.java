package shop.gaship.scheduler.graderenewal.config;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 회원승급 관련 job 설정 configuration.
 *
 * @author : 김세미
 * @since 1.0
 */
@Configuration
@RequiredArgsConstructor
public class GradeRenewalJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final GradeRenewalStepConfig stepConfig;

    /**
     * 회원승급을 진행하는 job.
     *
     * @return Job
     * @author 김세미
     */
    @Bean
    public Job renewalJob() {
        return jobBuilderFactory.get("grade-renewal_" + LocalDateTime.now())
                .start(stepConfig.prepareMemberGradeList())
                .next(stepConfig.progressGradeRenewal())
                .build();
    }
}