package com.home.pratice.bootstrap.common.factory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Component
@Configuration
@PropertySource(value = { "file:${PROJECT_CONFIG_PATH}/db.properties" }, encoding = "UTF-8")
public class OracleFactory {
    @Value("${oracle.username}")
    private String username;
    @Value("${oracle.password}")
    private String password;
    @Value("${oracle.url}")
    private String url;
    @Value("${oracle.driverclassname}")
    private String driverclassname;

    private final String datasourceName = "oracleDataSource";

    @Bean("oracleDataSource")
    public DataSource oracleDataSource() {
        return DataSourceBuilder
                .create()
                .username(username)
                .password(password)
                .url(url)
                .driverClassName(driverclassname)
                .build();
    }

    @Bean(name = "oracleManager")
    public PlatformTransactionManager oracleManager(@Qualifier(datasourceName) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "oracleJDBC")
    public JdbcTemplate oracleJDBC(@Qualifier(datasourceName) DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
