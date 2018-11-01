package com.github.kanon.common.base.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description: 用户
 * @Date: 2018/6/19
 */
@Data
public class UserVo extends BaseVo{
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Integer userId;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String userPass;

    /**
     * 是否超级管理员
     */
    private Boolean isSuper;

    /**
     * 随机盐
     */
    private String salt;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 0-正常，1-停用,2-冻结
     */
    private Integer status;

    /**
     * 角色列表
     */
    private List<RoleVo> roleList;
}
