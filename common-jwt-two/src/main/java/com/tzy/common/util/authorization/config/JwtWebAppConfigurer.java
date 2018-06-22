package com.tzy.common.util.authorization.config;

import com.tzy.common.util.authorization.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration

public class JwtWebAppConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor()).addPathPatterns("/**").
                excludePathPatterns("/login","/swagger-resources","/v2/api-docs");
//        super.addInterceptors(registry);
    }
}