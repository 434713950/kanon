package com.github.kanon.common.exceptions;

import lombok.Data;

/**
 * @author pengCheng
 * 请求出错异常
 */
@Data
public class ErrorMsgException  extends RuntimeException{

	private String msg;

	private String[] text;

	/**
	 * 是否要刷新页面
	 */
	private Boolean isRefresh;

	public ErrorMsgException(String msg,String... text){
		this(false,msg,text);
	}

	public ErrorMsgException(Boolean isRefresh,String msg,String... text){
		this.isRefresh = isRefresh;
		this.msg = msg;
		this.text=text;
	}
}
