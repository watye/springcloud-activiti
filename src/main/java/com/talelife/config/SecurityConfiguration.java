package com.talelife.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 安全认证配置
 *
 * @author：lwy
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    /**
     * 配置安全信息
     * - 禁用csrf功能
     * - 开启所有请求需要验证并且使用http basic进行认证
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

    	 http.csrf()
         .disable()
         .authorizeRequests()
         .anyRequest().authenticated()
         .and()
         .httpBasic();
    }
}
