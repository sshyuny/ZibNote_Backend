package com.sshyu.zibnote.configure.web;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sshyu.zibnote.application.service.auth.JwtUtil;
import com.sshyu.zibnote.configure.filter.JwtAuthFilter;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public JwtAuthFilter jwtAuthFilter(JwtUtil jwtUtil) {
        return new JwtAuthFilter(jwtUtil);
    }

    @Bean
    public FilterRegistrationBean<JwtAuthFilter> jwtFilter(JwtUtil jwtUtil) {
        FilterRegistrationBean<JwtAuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(jwtAuthFilter(jwtUtil));
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true); // 세션/쿠키 사용할 경우
    }
    
}
