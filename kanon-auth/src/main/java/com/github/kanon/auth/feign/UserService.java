package com.github.kanon.auth.feign;

import com.github.kanon.auth.feign.fallback.UserServiceFallbackImpl;
import com.github.kanon.common.base.vo.UserVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author pengCheng
 * @date 2017/10/28
 */
@FeignClient(name = "kanon-upms-service", fallback = UserServiceFallbackImpl.class)
public interface UserService {

    /**
     * 通过用户名查询用户、角色信息
     * @param username 用户名
     * @return UserVo
     */
    @GetMapping("/user/findUserByUsername/{username}")
    UserVo findUserByUsername(@PathVariable("username") String username);

    /**
     * 通过手机号、邮箱、账号查询用户、角色信息
     *
     * @param loginName 登录名，包含用户的手机号、邮箱、账号登录
     * @return UserVo
     */
    @GetMapping("/user/findUserByLogin/{loginName}")
    UserVo findUserByLogin(@PathVariable("loginName") String loginName);

    /**
     * 根据OpenId查询用户信息
     * @param openId openId
     * @return UserVo
     */
    @GetMapping("/user/findUserByOpenId/{openId}")
    UserVo findUserByOpenId(@PathVariable("openId") String openId);
}
