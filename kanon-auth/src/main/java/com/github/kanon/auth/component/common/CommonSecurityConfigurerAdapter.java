package com.github.kanon.auth.component.common;

import com.github.kanon.auth.feign.UserService;
import com.github.kanon.auth.handler.SecurityAuthenticationFailureHandler;
import com.github.kanon.auth.handler.SecurityAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @Author: PengCheng
 * @Description: spring security通用认证配置
 * @Date: 2018/8/3
 */
@Component
public class CommonSecurityConfigurerAdapter extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityAuthenticationSuccessHandler securityAuthenticationSuccessHandler;

    @Autowired
    private SecurityAuthenticationFailureHandler securityAuthenticationFailureHandler;

    @Override
    public void configure(HttpSecurity http) {
        //过滤器链
        AbstractAuthenticationProcessingFilter authenticationProcessingFilter = new CommonAuthenticationFilter();
        authenticationProcessingFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        authenticationProcessingFilter.setAuthenticationSuccessHandler(securityAuthenticationSuccessHandler);
        authenticationProcessingFilter.setAuthenticationFailureHandler(securityAuthenticationFailureHandler);

        AuthenticationProvider authenticationProvider = new CommonAuthenticationProvider(userService,passwordEncoder);

        http.authenticationProvider(authenticationProvider)
                .addFilterAfter(authenticationProcessingFilter,UsernamePasswordAuthenticationFilter.class);
    }
}
