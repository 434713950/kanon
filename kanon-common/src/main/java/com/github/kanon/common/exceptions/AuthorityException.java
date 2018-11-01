package com.github.kanon.common.exceptions;


import com.github.kanon.common.constants.MessageConstants;

/**
 * @Author: PengCheng
 * @Description:    权限不足异常
 * @Date: 2018/4/13
 */
public class AuthorityException extends RuntimeException{

    public static final Integer CODE =MessageConstants.OPTION_NOT_AUTHORITY_CODE;

    public static final String MSG = MessageConstants.OPTION_NOT_AUTHORITY_MSG;

}
