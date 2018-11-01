package com.github.kanon.common.exceptions;

import com.github.kanon.common.constants.MessageConstants;
import lombok.Data;

/**
 * @author pengCheng
 * 请求出错异常
 */
@Data
public class ErrorMsgException  extends RuntimeException{

	public static final Integer CODE = MessageConstants.OPTION_FAILED_CODE;

	private String msg;

	private String[] text;

	public ErrorMsgException(String msg,String... text){
		this.msg = msg;
		this.text=text;
	}
}
