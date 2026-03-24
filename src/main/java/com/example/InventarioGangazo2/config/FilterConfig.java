package com.example.InventarioGangazo2.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.InventarioGangazo2.filter.JwtValidationFilter;

@Configuration
public class FilterConfig {
    @Bean
    FilterRegistrationBean<JwtValidationFilter> JwtFilter(JwtValidationFilter jwtValidatorFilter) {
        FilterRegistrationBean<JwtValidationFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(jwtValidatorFilter);

        registrationBean.addUrlPatterns("/*");

        registrationBean.setOrder(1);

        return registrationBean;
    }
}
