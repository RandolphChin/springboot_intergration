package com.shaphar.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers(
                "/swagger-ui.html",
                "/swagger-resources/**",//用来获取api-docs的URI
              //  "/swagger/**",
                "/v2/api-docs",//swagger api json
           //     "/swagger-resources/configuration/ui",//用来获取支持的动作
           //     "/swagger-resources/configuration/security",//安全选项
                "/actuator/**",
                "/webjars/**"
        );
    }
    // 用于解决 Restful Post 请求时需要登录验证，服务端返回 403  错误，原因是 post请求无法验证输入用户名及密码
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().disable();
    }
}
