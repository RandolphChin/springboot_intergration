package com.shaphar.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * extends WebMvcConfigurerAdapter 更改为 implements WebMvcConfigurer
 * 用以解决SpringBoot2 WebMvcConfigurerAdapter 过时问题
 */
@Configuration
public class WebMvcConfig  implements WebMvcConfigurer {
    //为ws.HTML 提供便捷的路径映射。
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/ws").setViewName("/ws");
    }

    @Bean
    public FilterRegistrationBean filterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new SimpleCORSFilter());
        List<String> urlList = new ArrayList<String>();
        urlList.add("/*");
        registration.setUrlPatterns(urlList);
        registration.setName("UrlFilter");
        registration.setOrder(1);
        return registration;
    }
}
