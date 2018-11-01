package com.github.kanon.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kanon.common.constants.CommonConstant;
import com.github.kanon.common.exceptions.ErrorMsgException;
import com.github.kanon.common.utils.system.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * @Author: PengCheng
 * @Description:  spring security 认证失败处理器
 * @Date: 2018/8/3
 */
@Slf4j
@Component
public class SecurityAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
            String[] tokens = AuthUtils.extractAndDecodeHeader(request);
            assert tokens.length == 2;
            String clientId = tokens[0];

            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
            TokenRequest tokenRequest = new TokenRequest(new HashMap(), clientId, clientDetails.getScope(), "mobile");
            OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

            OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
            OAuth2AccessToken oAuth2AccessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
            log.info("achieve token success：{}", oAuth2AccessToken.getValue());

            response.setCharacterEncoding(CommonConstant.CONTENT_ENCODE);
            response.setContentType(CommonConstant.CONTENT_TYPE);
            PrintWriter printWriter = response.getWriter();
            printWriter.append(objectMapper.writeValueAsString(oAuth2AccessToken));
        } catch (IOException | ErrorMsgException e) {
            throw new BadCredentialsException(
                    "Failed to decode basic authentication token");
        }
    }
}
