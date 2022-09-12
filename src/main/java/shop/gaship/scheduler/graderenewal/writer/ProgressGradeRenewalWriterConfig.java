package shop.gaship.scheduler.graderenewal.writer;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shop.gaship.scheduler.graderenewal.dto.RenewalMemberGradeRequestDto;


/**
 * 회원등급이 갱신되는 정보를 최종적으로 Update 하고 Insert 하는 writer 를 설정하는 configuration 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Configuration
@RequiredArgsConstructor
public class ProgressGradeRenewalWriterConfig {
    @Qualifier("shopDataSource")
    private final DataSource shopDataSource;

    /**
     * 회원의 등급 정보를 갱신하는 writer 입니다.
     *
     * @return JdbcBatchItemWriter 를 반환합니다.
     * @author 김세미
     */
    @Bean
    public JdbcBatchItemWriter<RenewalMemberGradeRequestDto> memberRenewalWriter() {
        return new JdbcBatchItemWriterBuilder<RenewalMemberGradeRequestDto>()
                .dataSource(shopDataSource)
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("update members set member_grade_no = :memberGradeNo, "
                        + "next_renewal_grade_date = :nextRenewalGradeDate "
                        + "where member_no = :memberNo")
                .itemPreparedStatementSetter((item, ps) -> {
                    ps.setInt(1, item.getMemberGradeNo());
                    ps.setString(2, item.getNextRenewalGradeDate()
                            .format(DateTimeFormatter.ofPattern("yyyyMMdd")));
                    ps.setInt(3, item.getMemberNo());
                })
                .columnMapped()
                .build();
    }

    /**
     * 회원의 등급 갱신 이력을 저장하는 writer 입니다.
     *
     * @return JdbcBatchItemWriter 를 반환합니다.
     * @author 김세미
     */
    @Bean
    public JdbcBatchItemWriter<RenewalMemberGradeRequestDto> gradeHistoryWriter() {
        return new JdbcBatchItemWriterBuilder<RenewalMemberGradeRequestDto>()
                .dataSource(shopDataSource)
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .itemPreparedStatementSetter((item, ps) -> {
                    ps.setInt(1, item.getMemberNo());
                    ps.setLong(2, item.getTotalAmount());
                    ps.setString(3, item.getGradeName());
                    ps.setString(4, item.getAt()
                            .format(DateTimeFormatter.ofPattern("yyyyMMdd")));
                })
                .sql("insert into grade_histories "
                        + "(member_no, grade_total_amount, grade_name, grade_at) "
                        + "values (:memberNo, :totalAmount, :gradeName, :at)")
                .columnMapped()
                .build();
    }

    /**
     * 회원의 등급정보 변경 후 갱신 이력 저장을 순차적으로 하기위한 writer 입니다.
     *
     * @return CompositeItemWriter 를 반환합니다.
     * @author 김세미
     */
    @Bean
    public CompositeItemWriter<RenewalMemberGradeRequestDto> progressGradeRenewalWriter() {
        CompositeItemWriter<RenewalMemberGradeRequestDto> progressGradeRenewalCompositeWriter =
                new CompositeItemWriter<>();

        progressGradeRenewalCompositeWriter
                .setDelegates(Arrays.asList(memberRenewalWriter(), gradeHistoryWriter()));

        return progressGradeRenewalCompositeWriter;
    }
}
