package com.github.kanon.common.exceptions;


import com.github.kanon.common.constants.MessageConstants;

/**
 * @author PengCheng
 * 未登录异常
 */
public class LoginNotException extends RuntimeException{

    public static final Integer CODE =MessageConstants.OPTION_NOT_LOGIN_CODE;

    public static final String MSG = MessageConstants.OPTION_LOGIN_NOT_MSG;
}
