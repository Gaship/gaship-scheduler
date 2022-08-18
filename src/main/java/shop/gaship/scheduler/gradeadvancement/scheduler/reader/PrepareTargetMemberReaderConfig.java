package shop.gaship.scheduler.gradeadvancement.scheduler.reader;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import shop.gaship.scheduler.gradeadvancement.scheduler.dto.GradeRenewalTargetDto;


/**
 * 회원 등급 갱신 대상 회원 조회 reader 의 설정을 위한 configuration 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Configuration
@RequiredArgsConstructor
public class PrepareTargetMemberReaderConfig {
    @Qualifier("shopDataSource")
    private final DataSource shopDataSource;

    /**
     * 등급 갱신 대상 회원 정보를 조회하는 JdbcPagingItemReader 를
     * 빈으로 등록하는 메서드 입니다.
     *
     * @return 등급 갱신 대상 회원 정보를 조회하는 JdbcPagingItemReader 를 반환합니다.
     */
    @Bean
    public JdbcPagingItemReader<GradeRenewalTargetDto> prepareTargetMemberReader() {

        return new JdbcPagingItemReaderBuilder<GradeRenewalTargetDto>()
                .dataSource(shopDataSource)
                .rowMapper(new BeanPropertyRowMapper<>(GradeRenewalTargetDto.class))
                .queryProvider(renewalTargetQueryProvider())
                .name("prepareTargetMemberReader")
                .saveState(false)
                .build();
    }

    /**
     * 회원의 다음등급갱신일자를 통해 현재 등급 갱신 대상을 조회하는 query 를 제공하는
     * PagingQueryProvider 를 빈으로 등록하는 메서드입니다.
     *
     * @return MySqlPagingQueryProvider 를 반환합니다.
     */
    @Bean
    public PagingQueryProvider renewalTargetQueryProvider() {
        LocalDateTime renewalDate = LocalDateTime.now();

        if (renewalDate.getHour() == 23) {
            renewalDate = renewalDate.plusHours(1L);
        }

        Map<String, Order> sorts = new HashMap<>();
        sorts.put("memberNo", Order.ASCENDING);

        MySqlPagingQueryProvider queryProvider =
                new MySqlPagingQueryProvider();

        queryProvider
                .setSelectClause("select target.member_no as memberNo, "
                + "target.next_renewal_grade_date as renewalGradeDate");
        queryProvider
                .setFromClause("from members target");
        queryProvider
                .setWhereClause("where target.next_renewal_grade_date = "
                + renewalDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        queryProvider
                .setSortKeys(sorts);

        return queryProvider;
    }
}
