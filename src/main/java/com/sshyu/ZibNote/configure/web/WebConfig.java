package com.sshyu.zibnote.configure.web;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sshyu.zibnote.application.service.auth.JwtUtil;
import com.sshyu.zibnote.configure.filter.JwtAuthFilter;
import com.sshyu.zibnote.configure.filter.CustomCorsFilter;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public JwtAuthFilter jwtAuthFilter(JwtUtil jwtUtil) {
        return new JwtAuthFilter(jwtUtil);
    }
    @Bean
    public CustomCorsFilter customCorsFilter() {
        return new CustomCorsFilter();
    }

    @Bean
    public FilterRegistrationBean<JwtAuthFilter> jwtAuthFilterRegister(JwtUtil jwtUtil) {
        FilterRegistrationBean<JwtAuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(jwtAuthFilter(jwtUtil));
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<CustomCorsFilter> customCorsFilterRegister() {
        FilterRegistrationBean<CustomCorsFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(customCorsFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(0);
        return registrationBean;
    }
    
}
