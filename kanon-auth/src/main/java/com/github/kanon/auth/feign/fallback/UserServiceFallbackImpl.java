package com.github.kanon.auth.feign.fallback;

import com.github.kanon.auth.feign.UserService;
import com.github.kanon.common.base.model.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author pengCheng
 * @date 2017/10/28
 * 用户服务的fallback
 */
@Service
@Slf4j
public class UserServiceFallbackImpl implements UserService {

    /**
     * 通过用户名查询用户、角色信息
     * @param username 用户名
     * @return
     */
    @Override
    public UserVo findUserByUsername(String username) {
        log.error("调用{}异常:{}", "通过用户名查询用户", username);
        return null;
    }

    /**
     * 通过手机号查询用户、角色信息
     * @param mobile 手机号
     * @return UserVo
     */
    @Override
    public UserVo findUserByLogin(String mobile) {
        log.error("调用{}异常:{}", "通过手机号查询用户", mobile);
        return null;
    }

    /**
     * 根据OpenId查询用户信息
     *
     * @param openId openId
     * @return UserVo
     */
    @Override
    public UserVo findUserByOpenId(String openId) {
        log.error("调用{}异常:{}", "通过OpenId查询用户", openId);
        return null;
    }
}
