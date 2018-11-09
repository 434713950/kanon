package com.github.kanon.common.constants;

/**
 * @Author: PengCheng
 * @Description: 国际化常量
 * @Date: 2018/4/12
 */
public interface MessageConstants {
    /**
     *  成功消息
     */
    String OPTION_SUCCESS_MSG = "Operation Success";
    String OPTION_DELETE_SUCCESS_MSG = "Delete Success";
    String OPTION_ADD_SUCCESS_MSG = "Add Success";
    String OPTION_UPDATE_SUCCESS_MSG = "Update Success";
    String OPTION_UPLOAD_SUCCESS_MSG = "Upload Success";
    String OPTION_EXPORT_SUCCESS_MSG = "Export Success";

    /**
     * 成功code
     */
    Integer OPTION_SUCCESS_CODE = 200;

    /**
     * 系统内部错误
     */
    Integer SYSTEM_ERROR_CODE = 500;
    /**
     * 系统错误，请联系管理员！
     */
    String SYSTEM_FAILED_MSG = "系统错误，请联系管理员！";

    /**
     * 验证参数错误
     */
    Integer OPTION_FAILED_CODE = 406;

    /**
     * 验证参数错误并通知前台刷新
     */
    Integer OPTION_FAILED_REFRESH_CODE = 50;

    /**
     * 权限不足信息
     */
    Integer OPTION_NOT_AUTHORITY_CODE = 405;
    /**
     * 您没有访问权限！
     */
    String OPTION_NOT_AUTHORITY_MSG = "您没有访问权限！";


    /**
     * 初始密码
     */
    Integer OPTION_INITIAL_PASSWORD_CODE = 20;
    /**
     * 当前密码为初始密码，请修改！
     */
    String OPTION_INITIAL_PASSWORD_FIRST = "当前密码为初始密码，请修改！";

    /**
     * 未登陆信息
     */
    Integer OPTION_NOT_LOGIN_CODE = 403;
    /**
     * 未登录
     */
    String OPTION_LOGIN_NOT_MSG = "未登录";
}
