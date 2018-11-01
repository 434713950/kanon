package com.github.kanon.gateway.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: PengCheng
 * @Description:  认证许可服务
 * @Date: 2018/9/12
 */
public interface PermissionService {
    /**
     * 判断请求是否有权限
     *
     * @param request        HttpServletRequest
     * @param authentication 认证信息
     * @return 是否有权限
     */
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
