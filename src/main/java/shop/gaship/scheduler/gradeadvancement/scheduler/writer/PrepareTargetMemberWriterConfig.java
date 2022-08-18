package shop.gaship.scheduler.gradeadvancement.scheduler.writer;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shop.gaship.scheduler.gradeadvancement.scheduler.dto.ConvertedTargetDto;


/**
 * 변환된 등급 갱신 대상회원 정보를 batch db 에 저장하기 위한 writer 설정 configuration 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Configuration
@RequiredArgsConstructor
public class PrepareTargetMemberWriterConfig {
    @Qualifier("jobDataSource")
    private final DataSource jobDataSource;

    /**
     * 변환된 등급 갱신 대상 회원 정보를
     * batch db 에 저장하는 writer 를 빈으로 등록하는 메서드 입니다.
     *
     * @return JdbcBatchItemWriter 를 반환합니다.
     */
    @Bean
    public JdbcBatchItemWriter<ConvertedTargetDto> prepareTargetMemberWriter() {
        return new JdbcBatchItemWriterBuilder<ConvertedTargetDto>()
                .dataSource(jobDataSource)
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .itemPreparedStatementSetter((item, ps) -> {
                    ps.setInt(1, item.getMemberNo());
                    ps.setString(2, item.getRenewalGradeDate());
                    ps.setBoolean(3, item.getIsComplete());
                })
                .sql("insert into advancement_target (member_no, renewal_grade_date, is_complete) "
                        + "values (:memberNo, :renewalGradeDate, :isComplete)")
                .columnMapped()
                .build();
    }
}
