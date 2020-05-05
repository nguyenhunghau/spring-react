/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.management;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.management.repository",
        entityManagerFactoryRef = "firstEntityManagerFactory",
        transactionManagerRef = "firstTransactionManager"
)
public class DdConfig {

    @Value("${jdbc.driver-class-name}")
    private String driver;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String pass;

    @Value("${hibernate.dialect}")
    private String dialect;
    @Value("${jdbc.show-sql}")
    private String showSql;
    @Value("${hibernate.ddl-auto}")
    private String ddlAuto;
    @Value("${hibernate.use-new-id-generator-mappings}")
    private String newIdGeneratorMappings;

//    @Primary
    @Bean
    public DataSource firstDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driver);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(pass);
        return new HikariDataSource(config);
    }

    @Bean
    public PlatformTransactionManager firstTransactionManager() {
        return new JpaTransactionManager(firstEntityManagerFactory().getObject());
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean firstEntityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setDataSource(firstDataSource());
        factory.setPackagesToScan("com.example.management.entity");

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", dialect);
        properties.put("hibernate.show-sql", showSql);
        properties.put("hibernate.hbm2ddl.auto", ddlAuto);
        properties.put("hibernate.use-new-id-generator-mappings", newIdGeneratorMappings);
        factory.setJpaPropertyMap(properties);

        return factory;
    }
}
