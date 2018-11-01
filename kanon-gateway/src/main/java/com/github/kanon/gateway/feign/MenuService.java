package com.github.kanon.gateway.feign;

import com.github.kanon.common.base.entity.Menu;
import com.github.kanon.gateway.feign.fallback.MenuServiceFallbackImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

/**
 * @Author: PengCheng
 * @Description:  菜单feign
 * @Date: 2018/9/12
 */
@FeignClient(name = "${server.upms.name}", fallback = MenuServiceFallbackImpl.class)
public interface MenuService {
    /**
     * 通过角色名查询菜单
     *
     * @param role 角色名称
     * @return 菜单列表
     */
    @GetMapping(value = "/menu/findMenuByRole/{role}")
    Set<Menu> findMenuByRole(@PathVariable("role") String role);
}
