package shop.gaship.scheduler.coupongenerationissue.scheduler.config;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import shop.gaship.scheduler.coupongenerationissue.scheduler.domain.MemberNumber;
import shop.gaship.scheduler.coupongenerationissue.scheduler.jobparam.CouponGenerationIssueJobParameter;
import shop.gaship.scheduler.coupongenerationissue.scheduler.processor.CouponGenerationIssueCreationRequestDtoProcessing;

/**
 * 쿠폰 생성 발급에 대한 스탭을 관리하는 클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@Configuration
@RequiredArgsConstructor
public class CouponGenerationIssueStepConfig {
    private final DataSource shopDataSource;

    @Qualifier("couponDataSource")
    private final DataSource couponDataSource;

    private final StepBuilderFactory stepBuilderFactory;

    private static final Integer CHUNK_SIZE = 1000;

    @Bean
    @JobScope
    public Step prepareMemberNoList() throws Exception {
        return stepBuilderFactory.get("회원 등급에 맞는 회원번호들을 얻어오는 step")
            .allowStartIfComplete(true)
            .<MemberNumber, CouponGenerationIssueCreationRequestDtoProcessing>chunk(CHUNK_SIZE)
            .reader(prepareMemberNoListReader())
            .processor(processor())
            .writer(prepareMemberNoListWriter())
            .build();
    }

    @Bean
    @StepScope
    public CouponGenerationIssueJobParameter couponGenerationIssueJobParameter() {
        return new CouponGenerationIssueJobParameter();
    }

    @Bean
    @StepScope
    public JdbcPagingItemReader<MemberNumber> prepareMemberNoListReader() throws Exception {
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("memberGradeNo",
            couponGenerationIssueJobParameter().getMemberGradeNo());

        return new JdbcPagingItemReaderBuilder<MemberNumber>()
            .pageSize(CHUNK_SIZE)
            .dataSource(shopDataSource)
            .rowMapper(new BeanPropertyRowMapper<>(MemberNumber.class))
            .queryProvider(customQueryProvider())
            .parameterValues(parameterValues)
            .name("jdbcPagingItemReader")
            .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<MemberNumber, CouponGenerationIssueCreationRequestDtoProcessing> processor() {

        return memberNumber -> {

            Integer couponTypeNo = couponGenerationIssueJobParameter().getCouponTypeNo();
            Integer memberNo = memberNumber.getMemberNo();
            LocalDateTime generation =
                couponGenerationIssueJobParameter().getGenerationDatetime();
            LocalDateTime issue = couponGenerationIssueJobParameter().getIssueDatetime();
            LocalDateTime expiration =
                couponGenerationIssueJobParameter().getExpirationDatetime();

            return new CouponGenerationIssueCreationRequestDtoProcessing(couponTypeNo, memberNo,
                generation, issue, expiration);
        };
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<CouponGenerationIssueCreationRequestDtoProcessing> prepareMemberNoListWriter() {

        return new JdbcBatchItemWriterBuilder<CouponGenerationIssueCreationRequestDtoProcessing>()
            .dataSource(couponDataSource)
            .sql("insert into coupon_generations_issues values " +
                "(null, " +
                ":couponTypeNo, " +
                ":memberNo, " +
                ":generationDatetime, " +
                ":issueDatetime, " +
                "null, " +
                ":expirationDatetime)")
            .beanMapped()
            .build();
    }

    public PagingQueryProvider customQueryProvider() throws Exception {
        SqlPagingQueryProviderFactoryBean queryProviderFactoryBean =
            new SqlPagingQueryProviderFactoryBean();

        queryProviderFactoryBean.setDataSource(shopDataSource);

        queryProviderFactoryBean.setSelectClause("SELECT member_no ");
        queryProviderFactoryBean.setFromClause("FROM members ");
        queryProviderFactoryBean.setWhereClause("WHERE member_grade_no = :memberGradeNo");

        Map<String, Order> sortKey = new HashMap<>();
        sortKey.put("member_no", Order.ASCENDING);

        queryProviderFactoryBean.setSortKeys(sortKey);

        return queryProviderFactoryBean.getObject();
    }
}
