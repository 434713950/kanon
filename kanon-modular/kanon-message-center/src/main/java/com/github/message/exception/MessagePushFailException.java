package com.github.message.exception;

/**
 * <p>消息发送失败异常</p>
 *
 * @author PengCheng
 * @date 2018/11/5
 */
public class MessagePushFailException extends RuntimeException{

    public MessagePushFailException(String message) {
        super(message);
    }

    public MessagePushFailException(String message, Throwable cause) {
        super(message, cause);
    }
}
