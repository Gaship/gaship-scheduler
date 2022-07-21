package shop.gaship.gashipscheduler.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
        value = {DataSourceConfig.class, DataProtectionConfig.class})
@TestPropertySource({
        "classpath:application-dev.properties",
        "classpath:application.properties"
})
class DataSourceConfigTest {
    @Autowired
    DataSourceConfig dataSourceConfig;

    @Test
    void dataSourceConfigTest() {
        assertThat(dataSourceConfig.getDriverClassName())
                .isEqualTo("com.mysql.cj.jdbc.Driver");
        assertThat(dataSourceConfig.getUrl())
                .isEqualTo("0a9480a606714b40af45ccb31fda725a");
        assertThat(dataSourceConfig.getUsername())
                .isEqualTo("gaship");
        assertThat(dataSourceConfig.getPassword())
                .isEqualTo("876b9add24b943869830b1919a7525ab");

    }
}