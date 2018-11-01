package com.github.kanon.common.exceptions;


import com.github.kanon.common.constants.MessageConstants;

/**
 * @Author: PengCheng
 * @Description:    初始密码异常
 * @Date: 2018/5/9
 */
public class InitialPasswordException extends RuntimeException{
    public static final Integer CODE =MessageConstants.OPTION_INITIAL_PASSWORD_CODE;

    public static final String MSG = MessageConstants.OPTION_INITIAL_PASSWORD_FIRST;
}
