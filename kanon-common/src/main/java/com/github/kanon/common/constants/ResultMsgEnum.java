package com.github.kanon.common.constants;

import lombok.Getter;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/12
 */
public enum ResultMsgEnum {

    SUCCESS(200,"操作成功"),
    SYSTEM_ERROR(500,"系统错误,请联系管理员!"),
    VALIDATION_ERROR(406,"参数校验失败!"),
    PREMISSION_DENIED(405,"您没有访问权限!"),
    INIT_PASS(20,"当前密码为初始密码,请修改!"),
    UN_LOGIN(403,"未登录");

    @Getter
    private Integer resultCode;

    @Getter
    private String resultMsg;

    ResultMsgEnum(Integer resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }
}
