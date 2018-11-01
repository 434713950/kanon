package com.github.kanon.auth.component.common;

import com.github.kanon.auth.exception.AuthFailException;
import com.github.kanon.auth.feign.UserService;
import com.github.kanon.auth.util.UserDetailsImpl;
import com.github.kanon.common.base.vo.UserVo;
import com.github.kanon.common.constants.UserStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author: PengCheng
 * @Description:    自定义身份认证验证组件
 * @Date: 2018/7/16
 */
public class CommonAuthenticationProvider implements AuthenticationProvider {

    @Setter@Getter
    private UserService userService;

    @Setter@Getter
    private PasswordEncoder passwordEncoder;

    public CommonAuthenticationProvider(UserService userService,PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CommonAuthenticationToken authenticationToken = (CommonAuthenticationToken) authentication;
        CommonAuthModel authModel = (CommonAuthModel) authentication.getPrincipal();

        UserDetailsImpl userDetails = buildUserDeatils(authModel);
        CommonAuthenticationToken finalAuthenticationToken = new CommonAuthenticationToken(userDetails,null, userDetails.getAuthorities());
        finalAuthenticationToken.setDetails(authenticationToken.getDetails());
        return authenticationToken;
    }


    private UserDetailsImpl buildUserDeatils(CommonAuthModel authModel){
        UserVo userVo = userService.findUserByUsername(authModel.getAccount());
        if (userVo == null || userVo.getStatus().equals(UserStatus.BLOCK)) {
            throw new AuthFailException("该账户不存在");
        }
        if (userVo.getStatus().equals(UserStatus.FROZEN)) {
            throw new AuthFailException("该账户已被冻结");
        }
        if (!passwordEncoder.matches(authModel.getPassword(),userVo.getUserPass())) {
            throw new AuthFailException("账户名或密码错误");
        }
        return new UserDetailsImpl(userVo);
    }


    /**
     * 是否可以提供输入类型的认证服务
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return CommonAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
