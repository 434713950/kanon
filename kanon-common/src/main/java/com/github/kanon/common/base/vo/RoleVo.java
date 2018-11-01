package com.github.kanon.common.base.vo;

import lombok.Data;

/**
 * @Author: PengCheng
 * @Description: 角色
 * @Date: 2018/6/19
 */
@Data
public class RoleVo extends BaseVo {
    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 是否系统固有的
     */
    private Boolean isSystemFixed;
}
