package com.erlis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

@Configuration
@EnableWebMvc
@PropertySource("classpath:/application.properties")
@ComponentScan(basePackages = {"com.erlis.controller", "com.erlis.dao", "com.erlis.validation"})
public class MvcConfig {

    @Bean
    public JdbcTemplate getTemplate(DataSource dataSource) {

        var populator = new ResourceDatabasePopulator();

        DatabasePopulatorUtils.execute(populator, dataSource);

        return new JdbcTemplate(dataSource);
    }

}