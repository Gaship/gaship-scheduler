package shop.gaship.gashipscheduler.scheduler.gradeadvancement.config;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shop.gaship.gashipscheduler.scheduler.gradeadvancement.GradeAdvancementItems;
import shop.gaship.gashipscheduler.scheduler.gradeadvancement.OutputData;
import shop.gaship.gashipscheduler.scheduler.gradeadvancement.item.GradeAdvancementProcessor;
import shop.gaship.gashipscheduler.scheduler.gradeadvancement.item.GradeAdvancementReader;
import shop.gaship.gashipscheduler.scheduler.gradeadvancement.item.GradeAdvancementWriter;

/**
 * 회원승급 관련 step & job 생성 config.
 *
 * @author : 김세미
 * @since 1.0
 */
@Configuration
@RequiredArgsConstructor
public class GradeAdvancementConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final GradeAdvancementReader reader;
    private final GradeAdvancementProcessor processor;
    private final GradeAdvancementWriter writer;

    /**
     * 회원승급에 필요한 데이터를 조회,처리,입력 하는 step.
     *
     * @return Step
     * @author 김세미
     */
    @Bean
    @JobScope
    public Step step() {
        return stepBuilderFactory.get("step")
                .allowStartIfComplete(true)
                .<GradeAdvancementItems, List<OutputData>>chunk(1)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    /**
     * 회원승급을 진행하는 job.
     *
     * @return Job
     * @author 김세미
     */
    @Bean
    public Job advanceJob() {
        return jobBuilderFactory.get(LocalDate.now().toString())
                .flow(step())
                .end()
                .build();
    }
}
