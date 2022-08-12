package shop.gaship.scheduler.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Properties;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;


/**
 * Mysql data source 관련 설정 Configuration 입니다.
 *
 * @author : 김세미
 * @author : 최겸준
 * @since 1.0
 */
@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {
    private final DataProtectionConfig dataProtectionConfig;
    @Value("${job-datasource.url}")
    private String jobUrl;
    @Value("${coupon-datasource.url}")
    private String couponUrl;
    @Value("${shopping-mall-datasource.url}")
    private String shopUrl;
    @Value("${datasource.data-source-class-name}")
    private String dataSourceClassName;
    @Value("${datasource.username}")
    private String username;
    @Value("${datasource.password}")
    private String password;


    /**
     * batch 환경에 필요한 JobRepository 관련 db 설정을 위한 DataSource 빈 등록 메서드 입니다.
     *
     * @return batch db 관련 DataSource 를 반환합니다.
     */
    @Primary
    @Bean(name = "jobDataSource")
    public DataSource jobDataSource() {

        return getDataSource(dataProtectionConfig.findSecretDataFromSecureKeyManager(jobUrl),
                dataProtectionConfig.findSecretDataFromSecureKeyManager(password));
    }

    /**
     * coupon db 설정을 위한 DataSource 빈 등록 메서드 입니다.
     *
     * @return coupon db 관련 DataSource 를 반환합니다.
     */
    @Bean(name = "couponDataSource")
    public DataSource couponDataSource() {

        return getDataSource(dataProtectionConfig.findSecretDataFromSecureKeyManager(couponUrl),
                dataProtectionConfig.findSecretDataFromSecureKeyManager(password));
    }


    /**
     * shopping mall db 설정을 위한 DataSource 빈 등록 메서드 입니다.
     *
     * @return shopping mall db 관련 DataSource 를 반환합니다.
     */
    @Bean(name = "shopDataSource")
    public DataSource shopDataSource() {

        return getDataSource(dataProtectionConfig.findSecretDataFromSecureKeyManager(shopUrl),
                dataProtectionConfig.findSecretDataFromSecureKeyManager(password));
    }

    private DataSource getDataSource(String secretUrl, String secretPassword) {
        Properties properties = new Properties();
        properties.setProperty("url", secretUrl);
        properties.setProperty("user", username);
        properties.setProperty("password", secretPassword);

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDataSourceClassName(dataSourceClassName);
        hikariConfig.setMaximumPoolSize(2);
        hikariConfig.setDataSourceProperties(properties);

        return new HikariDataSource(hikariConfig);
    }

    /**
     * couponDataSource 의 DataSourceTransactionManager 를 빈 등록하는 메서드 입니다.
     *
     * @return CouponDataSource 로 지정된 DataSourceTransactionManager 를 반환합니다.
     */
    @Bean
    public PlatformTransactionManager couponTransactionManager() {
        return new DataSourceTransactionManager(couponDataSource());
    }

    /**
     * jobDataSource 의 DataSourceTransactionManager 를 빈 등록하는 메서드 입니다.
     *
     * @return JobDataSource 로 지정된 DataSourceTransactionManager 를 반환합니다.
     */
    @Bean
    public PlatformTransactionManager jobTransactionManager() {
        return new DataSourceTransactionManager(jobDataSource());
    }

    /**
     * shopDataSource 의 DataSourceTransactionManager 를 빈 등록하는 메서드 입니다.
     *
     * @return ShopDataSource 로 지정된 DataSourceTransactionManager 를 반환합니다.
     */
    @Bean
    public PlatformTransactionManager shopTransactionManager() {
        return new DataSourceTransactionManager(shopDataSource());
    }

    /**
     * 쇼핑몰, 쿠폰, batch transaction 을 chaining 하여
     * PlatformTransactionManager 를 반환하는 빈 등록 메서드입니다.
     *
     * @param shopTransactionManager   쇼핑몰 db 관련 transactionManager 입니다.
     * @param couponTransactionManager 쿠폰 db 관련 transactionManager 입니다.
     * @param jobTransactionManager    batch db 관련 transactionManager 입니다.
    * @return 쇼핑몰, 쿠폰, batch transaction 을 chaining 한 PlatformTransactionManager 를 반환합니다.
     */
    @Primary
    @Bean
    public PlatformTransactionManager customTransactionManager(
            @Qualifier("shopTransactionManager") PlatformTransactionManager
                    shopTransactionManager,
            @Qualifier("couponTransactionManager") PlatformTransactionManager
                    couponTransactionManager,
            @Qualifier("jobTransactionManager") PlatformTransactionManager
                    jobTransactionManager) {

        return new ChainedTransactionManager(
                shopTransactionManager,
                couponTransactionManager,
                jobTransactionManager);
    }

    /**
     * Gets job url.
     *
     * @return the job url
     */
    public String getJobUrl() {
        return jobUrl;
    }

    /**
     * Gets coupon url.
     *
     * @return the coupon url
     */
    public String getCouponUrl() {
        return couponUrl;
    }

    /**
     * Gets shop url.
     *
     * @return the shop url
     */
    public String getShopUrl() {
        return shopUrl;
    }

    /**
     * Gets data source class name.
     *
     * @return the data source class name
     */
    public String getDataSourceClassName() {
        return dataSourceClassName;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }
}
