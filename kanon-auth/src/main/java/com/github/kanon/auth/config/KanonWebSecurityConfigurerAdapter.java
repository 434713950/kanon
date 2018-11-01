package com.github.kanon.auth.config;

import com.github.kanon.auth.component.common.CommonSecurityConfigurerAdapter;
import com.github.kanon.common.base.config.FilterIgnorePropertiesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/7/17
 */
@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER-1)
public class KanonWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    /**
     * 认证密码加密算法
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private FilterIgnorePropertiesConfig filterIgnorePropertiesConfig;

    @Autowired
    private CommonSecurityConfigurerAdapter commonSecurityConfigurerAdapter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        List<String> ignoreUrlList = filterIgnorePropertiesConfig.getUrls();
        http.cors().and()
                .csrf().disable()
                .apply(commonSecurityConfigurerAdapter)
                .and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //访问认证白名单
                .antMatchers(ignoreUrlList.toArray(new String[ignoreUrlList.size()])).permitAll()
                //未匹配的任何请求都要认证
                .anyRequest().authenticated();
        // 禁用缓存
        http.headers().cacheControl();
    }
}
