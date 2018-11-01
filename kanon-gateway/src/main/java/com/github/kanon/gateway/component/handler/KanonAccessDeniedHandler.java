package com.github.kanon.gateway.component.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kanon.common.base.vo.ResponseParam;
import com.github.kanon.common.constants.CommonConstant;
import com.github.kanon.common.constants.MessageConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: PengCheng
 * @Description:    OAuth2授权拒绝处理器
 * @Date: 2018/9/12
 */
@Slf4j
@Component
public class KanonAccessDeniedHandler extends OAuth2AccessDeniedHandler {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 授权拒绝处理
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException {
        log.info("authorization failed，access barred {}", request.getRequestURI());
        response.setCharacterEncoding(CommonConstant.CONTENT_ENCODE);
        response.setContentType(CommonConstant.CONTENT_TYPE);
        ResponseParam result = new ResponseParam(MessageConstants.OPTION_NOT_LOGIN_CODE,MessageConstants.OPTION_LOGIN_NOT_MSG);
        response.setStatus(HttpStatus.SC_FORBIDDEN);
        PrintWriter printWriter = response.getWriter();
        printWriter.append(objectMapper.writeValueAsString(result));
    }
}
