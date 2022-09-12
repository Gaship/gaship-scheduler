package shop.gaship.scheduler.graderenewal.reader;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import shop.gaship.scheduler.graderenewal.dto.MemberGradeResponseDto;


/**
 * 회원등급 데이터 준비를 위한 JdbcPagingItemReader 빈 등록 configuration 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Configuration
@RequiredArgsConstructor
public class PrepareMemberGradeReaderConfig {
    @Qualifier("shopDataSource")
    private final DataSource shopDataSource;

    /**
     * 회원등급 종류에 대한 데이터를 읽어오기 위한
     * JdbcPagingItemReader 를 빈으로 등록하는 메서드입니다.
     *
     * @return shopping mall db 로 부터 회원등급 종류에 대해 다건 조회하는 JdbcPagingItemReader 를 반환합니다.
     */
    @Bean
    @StepScope
    public JdbcPagingItemReader<MemberGradeResponseDto> prepareMemberGradeReader() {
        return new JdbcPagingItemReaderBuilder<MemberGradeResponseDto>()
                .dataSource(shopDataSource)
                .rowMapper(new BeanPropertyRowMapper<>(MemberGradeResponseDto.class))
                .queryProvider(memberGradeQueryProvider())
                .name("prepareMemberGradeReader")
                .saveState(false)
                .build();
    }

    /**
     * 회원등급 종류 다건 조회를 위한 PagingQueryProvider 를 빈으로 등록하는 메서드입니다.
     *
     * @return 회원등급 종류 다건 조회 쿼리를 제공하는 MySqlPagingQueryProvider 를 반환합니다.
     */
    @Bean
    public PagingQueryProvider memberGradeQueryProvider() {
        Map<String, Order> sorts = new HashMap<>();
        sorts.put("accumulateAmount", Order.ASCENDING);

        MySqlPagingQueryProvider queryProvider =
                new MySqlPagingQueryProvider();
        queryProvider
                .setSelectClause("select grade.member_grade_no as no,"
                        + "grade.name,"
                        + "grade.accumulate_amount as accumulateAmount,"
                        + "period.explanation as renewalPeriod");
        queryProvider
                .setFromClause("FROM member_grades grade "
                        + "INNER JOIN status_codes period "
                        + "ON grade.renewal_period_no = period.status_code_no");
        queryProvider
                .setSortKeys(sorts);

        return queryProvider;
    }
}
