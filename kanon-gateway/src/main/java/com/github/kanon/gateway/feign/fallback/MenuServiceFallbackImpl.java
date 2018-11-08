package com.github.kanon.gateway.feign.fallback;

import com.github.kanon.common.base.model.entity.Auth;
import com.github.kanon.gateway.feign.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @Author: PengCheng
 * @Description:  菜单feign回调
 * @Date: 2018/9/12
 */
@Slf4j
@Service
public class MenuServiceFallbackImpl implements MenuService {
    @Override
    public Set<Auth> findRoleAuth(String role) {
        log.error("调用{}异常{}","findMenuByRole",role);
        return new LinkedHashSet<>();
    }
}
