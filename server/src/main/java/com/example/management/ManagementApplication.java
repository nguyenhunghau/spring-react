package com.example.management;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@EnableEurekaServer
@SpringBootApplication(
        exclude = {
            org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,}
)
@EnableScheduling
public class ManagementApplication {

    @Value("${origin.cors.list}")
    private String cors;

    public static void main(String[] args) {
        SpringApplication.run(ManagementApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }
}
