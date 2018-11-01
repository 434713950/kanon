package com.github.kanon.common.exceptions;

/**
 * @Author: PengCheng
 * @Description:    http接口调用异常
 * @Date: 2018/4/24
 */
public class HttpClientException extends RuntimeException {

    public HttpClientException() {
    }

    public HttpClientException(String message) {
        super(message);
    }

    public HttpClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpClientException(Throwable cause) {
        super(cause);
    }

    public HttpClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
