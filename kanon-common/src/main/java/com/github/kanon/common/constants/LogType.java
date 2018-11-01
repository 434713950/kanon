package com.github.kanon.common.constants;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: PengCheng
 * @Description:    日志类别
 * @Date: 2018/9/13
 */
public enum LogType {

    COMMON(1,"正常"),
    ABNORMAL(0,"异常");

    @Getter@Setter
    private Integer code;

    @Getter@Setter
    private String msg;

    LogType(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
