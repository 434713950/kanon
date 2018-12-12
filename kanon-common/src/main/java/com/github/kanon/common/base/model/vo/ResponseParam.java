package com.github.kanon.common.base.model.vo;

import com.github.kanon.common.constants.ResultMsgEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author peng_cheng
 * 返回给客户端的统一外包json对象
 * @param <T>
 */
@Data
@ApiModel(description= "返回响应数据")
public class ResponseParam<T> {

	@ApiModelProperty(value = "信息code (0：系统内部错误；1：成功；2：外部错误；3：未登录；4：权限不足)")
	private int code;

	@ApiModelProperty(value = "返回的消息")
	private String message;

	@ApiModelProperty(value = "返回的数据")
	private T result;

	@ApiModelProperty(value = "返回的分页数据")
	private Pagination pagination;

	public ResponseParam(int code, String message, T result, Pagination pagination) {
		this(code,message);
		this.result = result;
		this.pagination = pagination;
	}

	public ResponseParam(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public ResponseParam(ResultMsgEnum resultMsgEnum) {
		this(resultMsgEnum.getResultCode(),resultMsgEnum.getResultMsg());
	}

	public ResponseParam(ResultMsgEnum resultMsgEnum, T result, Pagination pagination) {
		this(resultMsgEnum);
		this.result = result;
		this.pagination = pagination;
	}

	/**
	 * 返回成功信息
	 * @return			ResponseParam<K>
	 */
	public static ResponseParam success() {
		return new ResponseParam(ResultMsgEnum.SUCCESS);
	}

	/**
	 * 返回成功信息
	 * @param data		要携带的参数
	 * @param <K>		携带的参数类型
	 * @return			ResponseParam<K>
	 */
	public static <K> ResponseParam<K> success(K data) {
		return new ResponseParam<K>(ResultMsgEnum.SUCCESS, data,null);
	}

	/**
	 * 返回成功信息
	 * @param data		要携带的参数
	 * @param msg		要携带的信息
	 * @param <K>		携带的参数类型
	 * @return			ResponseParam<K>
	 */
	public static <K> ResponseParam<K> success(K data,String msg) {
		return new ResponseParam<K>(ResultMsgEnum.SUCCESS.getResultCode(), msg, data,null);
	}

	/**
	 * 返回成功信息
	 * @param data				要携带的参数
	 * @param pagination		要携带的分页信息
	 * @param <K>				携带的参数类型
	 * @return					ResponseParam<K>
	 */
	public static <K> ResponseParam<K> success(K data,Pagination pagination) {
		return new ResponseParam<K>(ResultMsgEnum.SUCCESS, data,pagination);
	}

	/**
	 * 返回系统代码错误信息（用于代码内部错误提示）
	 * @return	ResponseParam
	 */
	public static ResponseParam systemError(){
		return new ResponseParam(ResultMsgEnum.SYSTEM_ERROR,null,null);
	}
}
