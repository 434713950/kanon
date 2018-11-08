package com.github.kanon.gateway.service.impl;

import com.github.kanon.common.base.model.entity.Auth;
import com.github.kanon.gateway.feign.MenuService;
import com.github.kanon.gateway.service.PermissionService;
import com.github.pcutil.common.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: PengCheng
 * @Description:  许可认证服务
 * @Date: 2018/9/12
 */
@Slf4j
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private MenuService menuService;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        List<SimpleGrantedAuthority> grantedAuthorityList = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
        boolean hasPermission = false;

        if (principal != null) {
            if (CollectionUtil.isBlank(grantedAuthorityList)) {
                log.warn("角色列表为空：{}", authentication.getPrincipal());
                return hasPermission;
            }

            Set<Auth> authMethodSet = new HashSet<>();
            for (SimpleGrantedAuthority authority : grantedAuthorityList) {
                if (!StringUtils.equals(authority.getAuthority(), "ROLE_USER")) {
                    Set<Auth> roleAuthSet = menuService.findRoleAuth(authority.getAuthority());
                    if (CollectionUtil.isNotBlank(roleAuthSet)) {
                        authMethodSet.addAll(roleAuthSet);
                    }
                }
            }

            for (Auth auth : authMethodSet) {
                if (StringUtils.isNotEmpty(auth.getMethod()) &&
                        antPathMatcher.match(auth.getMethod(), request.getRequestURI()) &&
                        request.getMethod().equalsIgnoreCase(auth.getMethodType())) {
                    hasPermission = true;
                    break;
                }
            }
        }
        return hasPermission;
    }
}
