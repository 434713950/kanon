package com.github.kanon.common.base.model.entity;

import lombok.Data;

/**
 * <p>权限模型</p>
 *
 * @author PengCheng
 * @date 2018/11/6
 */
@Data
public class Auth {

    /**
     * 方法名
     */
    private String method;

    /**
     * 请求方式
     */
    private String methodType;
}
