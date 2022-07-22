package shop.gaship.scheduler.config;

import java.util.Objects;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.scheduler.dataprotection.dto.SecureKeyResponse;
import shop.gaship.scheduler.dataprotection.exception.NotFoundDataProtectionReposeData;


/**
 * 서버 환경설정.
 *
 * @author : 김세미
 * @since 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "secure-key-manager")
public class DataProtectionConfig {
    private String url;
    private String appKey;
    private String userInfoProtectionKey;

    @Bean
    public String userInformationProtectionValue() {
        return findSecretDataFromSecureKeyManager(userInfoProtectionKey);
    }

    String findSecretDataFromSecureKeyManager(String keyId) {
        String errorMessage = "응답 결과가 없습니다.";
        return Objects.requireNonNull(WebClient.create(url).get()
                        .uri("/keymanager/v1.0/appkey/{appkey}/secrets/{keyid}", appKey, keyId)
                        .retrieve()
                        .toEntity(SecureKeyResponse.class)
                        .blockOptional()
                        .orElseThrow(() -> new NotFoundDataProtectionReposeData(errorMessage))
                        .getBody())
                .getBody()
                .getSecret();
    }

    public String getUrl() {
        return url;
    }

    public String getAppKey() {
        return appKey;
    }

    public String getUserInfoProtectionKey() {
        return userInfoProtectionKey;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public void setUserInfoProtectionKey(String userInfoProtectionKey) {
        this.userInfoProtectionKey = userInfoProtectionKey;
    }
}
