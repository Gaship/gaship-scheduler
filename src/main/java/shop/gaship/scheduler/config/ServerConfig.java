package shop.gaship.scheduler.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 내부 서버 URL 관리.
 *
 * @author : 김세미
 * @since 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "gaship-server")
public class ServerConfig {
    private String authUrl;
    private String paymentsUrl;

    private String shoppingMallUrl;

    public String getAuthUrl() {
        return authUrl;
    }

    public String getPaymentsUrl() {
        return paymentsUrl;
    }

    public String getShoppingMallUrl() {
        return shoppingMallUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public void setPaymentsUrl(String paymentsUrl) {
        this.paymentsUrl = paymentsUrl;
    }

    public void setShoppingMallUrl(String shoppingMallUrl) {
        this.shoppingMallUrl = shoppingMallUrl;
    }
}
