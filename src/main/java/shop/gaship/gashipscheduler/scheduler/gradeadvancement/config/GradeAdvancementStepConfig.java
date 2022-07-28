package shop.gaship.gashipscheduler.scheduler.gradeadvancement.config;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shop.gaship.gashipscheduler.domain.membergrade.dto.request.RenewalMemberGradeRequestDto;
import shop.gaship.gashipscheduler.domain.membergrade.dto.response.AdvancementTargetResponseDto;
import shop.gaship.gashipscheduler.domain.membergrade.dto.response.MemberGradeResponseDto;
import shop.gaship.gashipscheduler.scheduler.gradeadvancement.dto.ConvertedTargetDataDto;
import shop.gaship.gashipscheduler.scheduler.gradeadvancement.entity.AdvancementTarget;
import shop.gaship.gashipscheduler.scheduler.gradeadvancement.processor.PrepareTargetMemberProcessor;
import shop.gaship.gashipscheduler.scheduler.gradeadvancement.processor.ProgressGradeAdvancementProcessor;
import shop.gaship.gashipscheduler.scheduler.gradeadvancement.reader.PrepareMemberGradeReader;
import shop.gaship.gashipscheduler.scheduler.gradeadvancement.reader.PrepareTargetMemberReader;
import shop.gaship.gashipscheduler.scheduler.gradeadvancement.writer.PrepareMemberGradeWriter;
import shop.gaship.gashipscheduler.scheduler.gradeadvancement.writer.PrepareTargetMemberWriter;
import shop.gaship.gashipscheduler.scheduler.gradeadvancement.writer.ProgressGradeAdvancementWriter;

/**
 * 회원 승급 관련 Step 설정 configuration.
 *
 * @author : 김세미
 * @since 1.0
 */
@Configuration
@RequiredArgsConstructor
public class GradeAdvancementStepConfig {
    private static final int CHUNK_SIZE = 5;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final PrepareMemberGradeReader prepareMemberGradeReader;
    private final PrepareMemberGradeWriter prepareMemberGradeWriter;
    private final PrepareTargetMemberReader prepareTargetMemberReader;
    private final PrepareTargetMemberProcessor prepareTargetMemberProcessor;
    private final PrepareTargetMemberWriter prepareTargetMemberWriter;
    private final ProgressGradeAdvancementProcessor progressGradeAdvancementProcessor;
    private final ProgressGradeAdvancementWriter progressGradeAdvancementWriter;

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
    @StepScope
    public Step prepareMemberGradeList() {
        return stepBuilderFactory.get("회원등급 종류 얻는 step")
                .allowStartIfComplete(true)
                .<List<MemberGradeResponseDto>, List<MemberGradeResponseDto>>chunk(CHUNK_SIZE)
                .reader(prepareMemberGradeReader)
                .writer(prepareMemberGradeWriter)
                .listener(promotionListener())
                .build();
    }

    /**
     * advanceJob 의 두번째 step.
     * 작업을 실행했을때 회원 승급 작업을 처리 하기 전
     * 승급 대상이 되는 회원 목록을 조회하여 batch db 에 저장한다.
     *
     * @return 승급 대상회원 데이터 처리 관련 로직이 포함된 Step 을 반환한다.
     */
    @Bean
    @StepScope
    public Step prepareTargetMemberList() {
        return stepBuilderFactory.get("승급 대상이 되는 회원 데이터를 준비하는 step")
                .allowStartIfComplete(true)
                .<List<AdvancementTargetResponseDto>, List<ConvertedTargetDataDto>>chunk(CHUNK_SIZE)
                .reader(prepareTargetMemberReader)
                .processor(prepareTargetMemberProcessor)
                .writer(prepareTargetMemberWriter)
                .build();
    }

    /**
     * batch db 에 저장되어 있는
     * 승급 대상회원 데이터를 읽어오기 위한 JpaPagingItemReader.
     *
     * @return AdvancementTarget type 의 데이터를 읽는 JpaPagingItemReader 를 반환한다.
     */
    @Bean
    public JpaPagingItemReader<AdvancementTarget> progressGradeAdvancementReader() {
        return new JpaPagingItemReaderBuilder<AdvancementTarget>()
                .name("progressGradeAdvancementReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(CHUNK_SIZE)
                .queryString("SELECT t FROM AdvancementTarget t "
                        + "WHERE nextRenewalGradeDate=" + LocalDate.now())
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
    public Step progressGradeAdvancement() {
        return stepBuilderFactory.get("회원 등급 갱신데이터 처리를 진행하는 step")
                .allowStartIfComplete(true)
                .<AdvancementTarget, RenewalMemberGradeRequestDto>chunk(CHUNK_SIZE)
                .reader(progressGradeAdvancementReader())
                .processor(progressGradeAdvancementProcessor)
                .writer(progressGradeAdvancementWriter)
                .build();
    }
}
