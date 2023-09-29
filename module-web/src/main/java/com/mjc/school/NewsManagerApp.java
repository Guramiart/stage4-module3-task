package com.mjc.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.mjc.school" })
public class NewsManagerApp extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(NewsManagerApp.class);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(NewsManagerApp.class);
    }

}
