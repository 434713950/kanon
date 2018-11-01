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
    String OPTION_SUCCESS_MSG = "操作成功";
    String OPTION_DELETE_SUCCESS_MSG = "删除成功";
    String OPTION_ADD_SUCCESS_MSG = "添加成功";
    String OPTION_UPDATE_SUCCESS_MSG = "更新成功";
    String OPTION_UPLOAD_SUCCESS_MSG = "上传成功";
    String OPTION_EXPORT_SUCCESS_MSG = "导出成功";

    /**
     * 成功code
     */
    Integer OPTION_SUCCESS_CODE = 200;

    /**
     * 系统内部错误
     */
    Integer SYSTEM_ERROR_CODE = 500;
    String SYSTEM_FAILED_MSG = "系统错误，请联系管理员！";

    /**
     * 验证参数错误
     */
    Integer OPTION_FAILED_CODE = 406;

    /**
     * 权限不足信息
     */
    Integer OPTION_NOT_AUTHORITY_CODE = 405;
    String OPTION_NOT_AUTHORITY_MSG = "您没有访问权限！";


    /**
     * 初始密码
     */
    Integer OPTION_INITIAL_PASSWORD_CODE = 20;
    String OPTION_INITIAL_PASSWORD_FIRST = "初始密码，请修改！";

    /**
     * 未登陆信息
     */
    Integer OPTION_NOT_LOGIN_CODE = 403;
    String OPTION_LOGIN_NOT_MSG = "未登录";
}
