package com.sshyu.zibnote.configure.web;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sshyu.zibnote.application.service.auth.JwtUtil;
import com.sshyu.zibnote.configure.filter.JwtAuthFilter;
import com.sshyu.zibnote.configure.filter.PassApiFilter;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public JwtAuthFilter jwtAuthFilter(JwtUtil jwtUtil) {
        return new JwtAuthFilter(jwtUtil);
    }
    @Bean
    public PassApiFilter passApiFilter() {
        return new PassApiFilter();
    }

    @Bean
    public FilterRegistrationBean<JwtAuthFilter> jwtAuthFilterRegister(JwtUtil jwtUtil) {
        FilterRegistrationBean<JwtAuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(jwtAuthFilter(jwtUtil));
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<PassApiFilter> passFilterRegister() {
        FilterRegistrationBean<PassApiFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(passApiFilter());
        registrationBean.addUrlPatterns("/pass/api/*");
        return registrationBean;
    }
    
}
