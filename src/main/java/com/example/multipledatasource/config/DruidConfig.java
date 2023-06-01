package com.example.multipledatasource.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;
import java.sql.SQLException;

@Slf4j
@Configuration
public class DruidConfig extends AbstractRoutingDataSource {
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.minPoolSize}")
    private int minPoolSize;
    @Value("${spring.datasource.maxPoolSize}")
    private int maxPoolSize;
    @Value("${spring.datasource.initialPoolSize}")
    private int initialPoolSize;
    @Value("${spring.datasource.druid.druidLoginUsername:root}")
    private String druidLoginUsername;
    @Value("${spring.datasource.druid.druidLoginPassword:qwe123456}")
    private String druidLoginPassword;

    @Bean
    @Primary
    public DataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setDriverClassName(driverClassName);
        datasource.setUrl(url);
        datasource.setUsername(username);
        datasource.setPassword(password);
        //其它配置
        datasource.setInitialSize(initialPoolSize);
        datasource.setMinIdle(minPoolSize);
        datasource.setMaxActive(maxPoolSize);
        datasource.setMaxWait(60000);
        datasource.setTimeBetweenEvictionRunsMillis(60000);
        datasource.setMinEvictableIdleTimeMillis(300000);
        datasource.setValidationQuery("SELECT 'x'");
        datasource.setTestWhileIdle(true);
        datasource.setTestOnBorrow(false);
        datasource.setTestOnReturn(false);
        datasource.setPoolPreparedStatements(true);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(20);
        try {
            datasource.setFilters("stat,wall,slf4j");
            datasource.init();
        } catch (SQLException e) {
            log.error("druid configuration initialization filter", e);
        }
        return datasource;
    }

    /**
     * 注册druid日志监控
     * 访问地址 <a href="http://localhost:8888/druid/login.html">druid日志监控</a>
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean<HttpServlet> druidStatViewServle2() {
        ServletRegistrationBean<HttpServlet> reg = new ServletRegistrationBean<>();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings("/druid/*");
        reg.addInitParameter("loginUsername", druidLoginUsername);
        reg.addInitParameter("loginPassword", druidLoginPassword);
        reg.addInitParameter("logSlowSql", "true");
        return reg;
    }

    /**
     * 注册一个：WebStatFilter
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<Filter> druidStatFilter2() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>(new WebStatFilter());

        /** 过滤规则 */
        filterRegistrationBean.addUrlPatterns("/*");
        /** 忽略资源 */
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }
}


