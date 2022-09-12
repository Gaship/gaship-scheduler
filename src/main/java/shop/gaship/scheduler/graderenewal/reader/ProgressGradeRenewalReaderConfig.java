package shop.gaship.scheduler.graderenewal.reader;

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
import shop.gaship.scheduler.graderenewal.dto.RenewalTargetMemberDto;

/**
 * 등급 갱신 진행을 위한 데이터를 조회하는 reader 설정을 위한 configuration 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Configuration
@RequiredArgsConstructor
public class ProgressGradeRenewalReaderConfig {

    @Qualifier("shopDataSource")
    private final DataSource shopDataSource;

    /**
     * 회원등급 갱신 대상 회원을 조회하는 reader 입니다.
     *
     * @return shopping mall db 에서 회원등급 갱신 대상 회원을 조회하는 JdbcPagingItemReader 를 반환합니다.
     * @author 김세미
     */
    @Bean
    public JdbcPagingItemReader<RenewalTargetMemberDto> progressRenewalTargetReader() {
        return new JdbcPagingItemReaderBuilder<RenewalTargetMemberDto>()
                .dataSource(shopDataSource)
                .rowMapper(new BeanPropertyRowMapper<>(RenewalTargetMemberDto.class))
                .saveState(false)
                .queryProvider(progressTargetQueryProvider())
                .build();
    }


    /**
     * 회원등급 대상 조회를 위한 query 를 제공하는 queryProvider 입니다.
     *
     * @return MySqlPagingQueryProvider 를 반환합니다.
     * @author 김세미
     */
    @Bean
    public PagingQueryProvider progressTargetQueryProvider() {
        LocalDateTime renewalDate = LocalDateTime.now();

        if (renewalDate.getHour() == 23) {
            renewalDate = renewalDate.plusHours(1L);
        }

        Map<String, Order> sorts = new HashMap<>();
        sorts.put("renewalGradeDate", Order.ASCENDING);

        MySqlPagingQueryProvider queryProvider =
                new MySqlPagingQueryProvider();
        queryProvider
                .setSelectClause("select a.member_no as memberNo, "
                + "a.renewalGradeDate, sum(b.amount) as accumulateAmount");
        queryProvider
                .setFromClause("from (select m.member_no, "
                + "m.next_renewal_grade_date as renewalGradeDate, o.order_no "
                + "from members m "
                + "left outer join orders o "
                + "on m.member_no = o.member_no) a "
                + "left outer join (select * from order_products p "
                + "    inner join status_codes c "
                + "        on c.status_code_no = p.order_status_no "
                + "                     where c.status_code_name='배송완료') b "
                + "on a.order_no = b.order_no");
        queryProvider
                .setGroupClause("group by a.member_no, a.renewalGradeDate "
                + "having a.renewalGradeDate < "
                + renewalDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        queryProvider
                .setSortKeys(sorts);

        return queryProvider;
    }
}
