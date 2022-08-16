package shop.gaship.scheduler.bean.datasource;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * multi datasource가 정상적으로 생성되고 주입되는지 확인하기 위한 테스트입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@SpringBootTest
@TestPropertySource("classpath:application.properties")
class DataSourceTest {
    @BatchDataSource
    @Autowired
    private DataSource jobDataSource;

    @Autowired
    private DataSource shopDataSource;

    @Qualifier(value = "couponDataSource")
    @Autowired
    private DataSource couponDataSource;

    @Test
    void test() throws SQLException {
        String jobUrl = jobDataSource.getConnection().getMetaData().getURL();
        String shopUrl = shopDataSource.getConnection().getMetaData().getURL();
        String couponUrl = couponDataSource.getConnection().getMetaData().getURL();

        assertThat(jobUrl)
            .contains("gaship_batch_test");
        assertThat(shopUrl)
            .contains("gaship_test");
        assertThat(couponUrl)
            .contains("gaship_coupon_test");
    }
}