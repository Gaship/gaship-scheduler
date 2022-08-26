package shop.gaship.scheduler.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * ServerConfig 테스트 코드.
 *
 * @author : 김세미
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = ServerConfig.class)
@TestPropertySource("classpath:application-prod.properties")
class ServerConfigTest {
    @Autowired
    ServerConfig config;

    @Test
    void serverConfigEnvironmentTest() {
        assertThat(config.getAuthUrl())
                .isEqualTo("http://192.168.0.74:7071");
        assertThat(config.getPaymentsUrl())
                .isEqualTo("http://192.168.0.57:7073");
        assertThat(config.getShoppingMallUrl())
                .isEqualTo("http://192.168.0.96");
    }
}
