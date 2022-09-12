package shop.gaship.scheduler.graderenewal.config;

import java.sql.SQLException;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import shop.gaship.scheduler.graderenewal.dto.MemberGradeResponseDto;
import shop.gaship.scheduler.graderenewal.dto.RenewalMemberGradeRequestDto;
import shop.gaship.scheduler.graderenewal.dto.RenewalTargetMemberDto;
import shop.gaship.scheduler.graderenewal.exception.MemberGradeEvaluateException;
import shop.gaship.scheduler.graderenewal.processor.ProgressGradeRenewalProcessor;
import shop.gaship.scheduler.graderenewal.writer.PrepareMemberGradeWriter;

/**
 * 회원 승급 관련 Step 설정 configuration.
 *
 * @author : 김세미
 * @since 1.0
 */
@Configuration
@RequiredArgsConstructor
public class GradeRenewalStepConfig {
    private static final int CHUNK_SIZE = 10;
    private final FixedBackOffPolicy backOffPolicy
            = new FixedBackOffPolicy();

    private final StepBuilderFactory stepBuilderFactory;
    private final JdbcPagingItemReader<MemberGradeResponseDto> prepareMemberGradeReader;
    private final PrepareMemberGradeWriter prepareMemberGradeWriter;
    private final JdbcPagingItemReader<RenewalTargetMemberDto> progressRenewalTargetReader;
    private final ProgressGradeRenewalProcessor progressGradeRenewalProcessor;
    private final CompositeItemWriter<RenewalMemberGradeRequestDto> progressGradeRenewalWriter;

    /**
     * step 간 데이터 공유를 위한 ExecutionContextPromotionListener.
     *
     * @return 데이터 공유에 필요한 key 값을 가진 ExecutionContextPromotionListener 가 반환 됩니다.
     */
    @Bean
    public ExecutionContextPromotionListener promotionListener() {
        ExecutionContextPromotionListener executionContextPromotionListener =
                new ExecutionContextPromotionListener();
        executionContextPromotionListener.setKeys(new String[]{"memberGradeList"});

        return executionContextPromotionListener;
    }

    /**
     * advanceJob 의 첫번째 step.
     * 작업을 실행했을때 먼저 회원등급 종류에 대한 데이터를 얻기 위한 Step.
     *
     * @return 회원등급 데이터 처리 관련 로직이 포함된 Step 을 반환한다.
     */
    @Bean
    @JobScope
    public Step prepareMemberGradeList() {
        backOffPolicy.setBackOffPeriod(3000);

        return stepBuilderFactory.get("회원등급 종류 얻는 step")
                .allowStartIfComplete(true)
                .<MemberGradeResponseDto, MemberGradeResponseDto>chunk(CHUNK_SIZE)
                .reader(prepareMemberGradeReader)
                .writer(prepareMemberGradeWriter)
                .faultTolerant()
                .retry(Exception.class)
                .retryLimit(3)
                .backOffPolicy(backOffPolicy)
                .listener(promotionListener())
                .build();
    }

    /**
     * advanceJob 의 마지막 처리 Step 으로
     * 앞의 step 에서 준비된 데이터를 통해 데이터 변환 작업을 거쳐
     * 회원정보의 등급 데이터 수정 요청과 등급이력 등록 요청을 보낸다.
     *
     * @return 최종적인 승급 관련 로직을 포함하는 Step 을 반환한다.
     */
    @Bean
    @JobScope
    public Step progressGradeRenewal() {
        backOffPolicy.setBackOffPeriod(5000);

        return stepBuilderFactory.get("회원 등급 갱신데이터 처리를 진행하는 step")
                .allowStartIfComplete(true)
                .<RenewalTargetMemberDto, RenewalMemberGradeRequestDto>chunk(CHUNK_SIZE)
                .reader(progressRenewalTargetReader)
                .processor(progressGradeRenewalProcessor)
                .writer(progressGradeRenewalWriter)
                .faultTolerant()
                .retry(MemberGradeEvaluateException.class)
                .retryLimit(3)
                .noRetry(SQLException.class)
                .skip(MemberGradeEvaluateException.class)
                .skipLimit(2)
                .backOffPolicy(backOffPolicy)
                .build();
    }
}
