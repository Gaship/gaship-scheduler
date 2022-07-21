package shop.gaship.gashipscheduler.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * SwaggerConfig 테스트 코드.
 *
 * @author : 김세미
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(SwaggerConfig.class)
class SwaggerConfigTest {
    @Autowired
    SwaggerConfig config;

    @Test
    void swaggerConfigTest(){
        Docket result = config.api();

        assertThat(result.getDocumentationType())
                .isEqualTo(DocumentationType.SWAGGER_2);
    }
}