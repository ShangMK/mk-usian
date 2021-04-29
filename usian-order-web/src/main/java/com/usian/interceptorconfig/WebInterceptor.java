package com.usian.interceptorconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebInterceptor implements WebMvcConfigurer {
    @Autowired
    UserIntercept userIntercept;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(userIntercept);
        interceptorRegistration.addPathPatterns("/frontend/orders/**");
        System.out.println("拦截了");
    }
}
