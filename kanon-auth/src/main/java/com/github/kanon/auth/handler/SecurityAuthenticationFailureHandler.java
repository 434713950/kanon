package com.github.kanon.auth.handler;

import com.alibaba.fastjson.JSON;
import com.github.kanon.auth.exception.AuthFailException;
import com.github.kanon.auth.exception.BadAccessTokenException;
import com.github.kanon.common.base.model.vo.ResponseParam;
import com.github.kanon.common.utils.spring.I18nUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;

/**
 * @Author: PengCheng
 * @Description: spring security 认证失败处理器
 * @Date: 2018/8/3
 */
@Slf4j
@Component
public class SecurityAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private I18nUtil i18nUtil;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        String errMsg = e.getMessage();
        Integer code = null;
        if (e instanceof AuthFailException){
            code = HttpURLConnection.HTTP_UNAUTHORIZED;
        }else if (e instanceof BadAccessTokenException){
            code = HttpURLConnection.HTTP_FORBIDDEN;
        }
        if (!StringUtils.isEmpty(errMsg)){
            errMsg = i18nUtil.i18nHandler(errMsg);
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(JSON.toJSONString(new ResponseParam(code,errMsg)));
    }
}
