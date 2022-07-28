package shop.gaship.gashipscheduler.scheduler.gradeadvancement.config;

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
public class GradeAdvancementJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final GradeAdvancementStepConfig stepConfig;

    /**
     * 회원승급을 진행하는 job.
     *
     * @return Job
     * @author 김세미
     */
    @Bean
    public Job advanceJob() {
        return jobBuilderFactory.get(LocalDateTime.now().toString())
                .start(stepConfig.prepareMemberGradeList())
                .next(stepConfig.prepareTargetMemberList())
                .next(stepConfig.progressGradeAdvancement())
                .build();
    }
}
