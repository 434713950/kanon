package com.github.kanon.auth.component.common;

import com.github.kanon.common.utils.system.AuthUtils;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: PengCheng
 * @Description: 普通登录验证过滤器
 * @Date: 2018/8/3
 */
public class CommonAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * 是否只支持post请求
     */
    @Getter
    @Setter
    private boolean postOnly = true;

    public CommonAuthenticationFilter() {
        super(new AntPathRequestMatcher(AuthUtils.SECURITY_LOGIN_URL,HttpMethod.POST.toString()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        if (postOnly && !request.getMethod().equals(HttpMethod.POST.name())){
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        CommonAuthModel loginModel = new ObjectMapper().readValue(request.getInputStream(),CommonAuthModel.class);
        CommonAuthenticationToken authenticationToken = new CommonAuthenticationToken(loginModel,null);

        setDetails(request,authenticationToken);

        return this.getAuthenticationManager().authenticate(authenticationToken);
    }

    protected void setDetails(HttpServletRequest request,
                              AbstractAuthenticationToken authRequest){
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}
