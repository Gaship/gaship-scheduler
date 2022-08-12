package shop.gaship.gashipscheduler.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * DataSourceConfig 테스트 코드.
 *
 * @author : 김세미
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(
        value = {DataProtectionConfig.class})
@TestPropertySource({
        "classpath:application-dev.properties",
        "classpath:application.properties"
})
@Import({DataSourceConfig.class})
class DataSourceConfigTest {
    @Autowired
    private DataSourceConfig dataSourceConfig;

    @Test
    void dataSourceConfigTest(){
        assertThat(dataSourceConfig.getUsername()).isEqualTo("gaship");
        assertThat(dataSourceConfig.getPassword()).isEqualTo("876b9add24b943869830b1919a7525ab");
        assertThat(dataSourceConfig.getDataSourceClassName()).isEqualTo("com.mysql.cj.jdbc.MysqlDataSource");
        assertThat(dataSourceConfig.getJobUrl()).isEqualTo("6604fb84a7a648bd9f78a3c09a70631c");
        assertThat(dataSourceConfig.getCouponUrl()).isEqualTo("2590912e7730471d851685ffc55ee6a9");
        assertThat(dataSourceConfig.getShopUrl()).isEqualTo("e67723aa5f3840ada297c5e6ee94799e");
    }
}