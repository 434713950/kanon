package com.github.kanon.auth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Author: PengCheng
 * @Description:   错误的授权令牌异常
 * @Date: 2018/8/3
 */
public class BadAccessTokenException extends AuthenticationException {

    public BadAccessTokenException(){
        super("bad auth token");
    }
}
