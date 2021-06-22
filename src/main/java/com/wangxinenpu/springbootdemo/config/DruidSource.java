package com.wangxinenpu.springbootdemo.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @ClassName: DruidSource
 * @Description: TODO
 * @Author: huc
 * @Date: 2021/6/1 10:50
 * @Version: v1.0
 */
@Slf4j
@Data
@Component
//@PropertySource("classpath:datasource.properties")
//@PropertySource("classpath:application.yml")
public class DruidSource {
    @Value("${cdc.from.linkurl}")
    private String dbUrl;
    @Value("${cdc.from.username}")
    private String username;
    @Value("${cdc.from.password}")
    private String password;
    @Value("${cdc.from.driverName}")
    private String driverClassName;
    @Value("${spring.druid.initialSize}")
    private int initialSize;
    @Value("${spring.druid.minIdle}")
    private int minIdle;
    @Value("${spring.druid.maxActive}")
    private int maxActive;
    @Value("${spring.druid.maxWait}")
    private int maxWait;
    @Value("${spring.druid.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${spring.druid.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;
    @Value("${spring.druid.validationQuery}")
    private String validationQuery;
    @Value("${spring.druid.testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${spring.druid.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${spring.druid.testOnReturn}")
    private boolean testOnReturn;
    @Value("${spring.druid.poolPreparedStatements}")
    private boolean poolPreparedStatements;
    @Value("${spring.druid.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;
    @Value("${spring.druid.filters}")
    private String filters;
    @Value("${spring.druid.connectionProperties}")
    private String connectionProperties;

    public DruidDataSource dataSource() throws SQLException {
        log.info("[DruidSource --> 初始化数据源配置]url:{},用户：{}，密码：{}", dbUrl, username, password);
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        //configuration
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        datasource.setFilters(filters);
        return datasource;
    }
}
