package com.github.sms.exception;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/11/1
 */
public class SmsSendException extends RuntimeException{

    public SmsSendException(String msg){
        super(msg);
    }
}
