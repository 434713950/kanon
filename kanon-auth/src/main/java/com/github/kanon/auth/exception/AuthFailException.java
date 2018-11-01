package com.github.kanon.auth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Author: PengCheng
 * @Description: 认证失败异常
 * @Date: 2018/8/3
 */
public class AuthFailException extends AuthenticationException {

    public AuthFailException(String msg) {
        super(msg);
    }
}
