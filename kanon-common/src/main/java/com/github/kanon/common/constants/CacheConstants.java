package com.github.kanon.common.constants;

/**
 * @Author: PengCheng
 * @Description:    zuul路由使用常量
 * @Date: 2018/9/12
 */
public interface CacheConstants {

    /**
     * 缓存组名
     */
    String KANON_CACHE_GROUP = "kanon";

    /**
     * 路由缓存key后缀
     */
    String CACHE_ROUTE_KEY_SUFFIX = "zuul_route";

    /**
     * 菜单树缓存key
     */
    String SYS_MENU_TREE_CACHE = "sys_menu_tree";


    /**
     * 服务验证码组名
     */
    String KANON_VERIFICATION_CACHE_GROUP = "kanon_verification";

    /**
     * 验证码缓存key
     */
    String VERIFICATION_CODE_CACHE_KEY_PRE = " VERIFICATION_CODE_";

}
