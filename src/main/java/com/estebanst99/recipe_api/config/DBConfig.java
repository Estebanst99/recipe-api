package com.estebanst99.recipe_api.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DBConfig {

    @Bean("postgresDS")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource postgresDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean("postgresTemplate")
    public JdbcTemplate postgresJdbcTemplate(@Qualifier("postgresDS") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}
