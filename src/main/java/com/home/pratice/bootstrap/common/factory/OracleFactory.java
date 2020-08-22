package com.home.pratice.bootstrap.common.factory;

import lombok.extern.slf4j.Slf4j;
import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;
import org.apache.commons.lang3.exception.ExceptionUtils;
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
import java.sql.SQLException;

@Slf4j
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
    private String driverClassName;
    @Value("${oracle.ucp.minPoolSize}")
    private String minPoolSize;
    @Value("${oracle.ucp.maxPoolSize}")
    private String maxPoolSize;

    private final String datasourceName = "oracleDataSource";

    @Bean("oracleDataSource")
    public DataSource oracleDataSource() {
        PoolDataSource pds = null;
        try {
            pds = PoolDataSourceFactory.getPoolDataSource();
            pds.setConnectionFactoryClassName(driverClassName);
            pds.setURL(url);
            pds.setUser(username);
            pds.setPassword(password);
            pds.setMinPoolSize(Integer.valueOf(minPoolSize));
            pds.setInitialPoolSize(10);
            pds.setMaxPoolSize(Integer.valueOf(maxPoolSize));
        } catch (SQLException e) {
           log.error("Error connecting to the database: " + ExceptionUtils.getStackTrace(e));
        }

        return pds;
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
