package com.github.kanon.auth.component.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: PengCheng
 * @Description:    通用登录认证使用的模型
 * @Date: 2018/6/24 16:28
 */
@Data
@ApiModel(value = "CommonLoginModel",description = "通用登录实体")
public class CommonAuthModel extends AuthModel{

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "密码")
    private String password;
}
