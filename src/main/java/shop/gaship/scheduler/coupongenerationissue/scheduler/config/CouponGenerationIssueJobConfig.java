package shop.gaship.scheduler.coupongenerationissue.scheduler.config;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 쿠폰생성발급 관련 job을 설정하는 class입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@Configuration
@RequiredArgsConstructor
public class CouponGenerationIssueJobConfig {

    private final JobBuilderFactory jobBuilderFactory;

    private final CouponGenerationIssueStepConfig couponGenerationIssueStepConfig;

    @Bean
    public Job couponGenerationIssueJob() throws Exception {
        return jobBuilderFactory.get(LocalDateTime.now().toString())
            .start(couponGenerationIssueStepConfig.prepareMemberNoList()).build();
    }
}
