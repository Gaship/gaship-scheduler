package shop.gaship.scheduler.config;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Objects;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
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
    private String localKey;

    @Bean
    public String userInformationProtectionValue() {
        return findSecretDataFromSecureKeyManager(userInfoProtectionKey);
    }

    String findSecretDataFromSecureKeyManager(String keyId) {
        String errorMessage = "응답 결과가 없습니다.";
        try {
            KeyStore clientStore = KeyStore.getInstance("PKCS12");
            clientStore.load(
                new ClassPathResource("github-action.p12").getInputStream(),
                localKey.toCharArray());

            SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
            sslContextBuilder.setProtocol("TLS");
            sslContextBuilder.loadKeyMaterial(clientStore, localKey.toCharArray());
            sslContextBuilder.loadTrustMaterial(new TrustSelfSignedStrategy());

            SSLConnectionSocketFactory sslConnectionSocketFactory =
                new SSLConnectionSocketFactory(sslContextBuilder.build());
            CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .build();
            HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory(httpClient);

            return Objects.requireNonNull(new RestTemplate(requestFactory)
                    .getForEntity(url + "/keymanager/v1.0/appkey/{appkey}/secrets/{keyid}",
                        SecureKeyResponse.class,
                        appKey,
                        keyId)
                    .getBody())
                .getBody()
                .getSecret();
        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException
                 | UnrecoverableKeyException | IOException | KeyManagementException e) {
            throw new NotFoundDataProtectionReposeData(errorMessage);
        }
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

    public void setLocalKey(String localKey) {
        this.localKey = localKey;
    }
}
