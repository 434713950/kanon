package com.github.kanon.common.utils.system;

import com.github.kanon.common.exceptions.ErrorMsgException;
import com.github.kanon.common.constants.CommonConstant;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

/**
 * @Author: peng_cheng
 * @Description: 认证工具类
 */
@Slf4j
public class AuthUtils {

    /**
     * 认证头信息前缀
     */
    public static final String AUTH_HEADER_PREFIX = "Kanon_";

    /**
     * 认证头
     */
    public static final String AUTH_HEADER = "Authorization";

    /**
     * 认证信息的定义边界符
     */
    public static final String AUTH_INFO_DELIMIT = ":";

    /**
     * 认证体系的登录路径
     */
    public static final String SECURITY_LOGIN_URL = "/login";

    /**
     * 服务id标志
     */
    public static final String SERVER_ID="server_id";


    /**
     * 从header 请求中的clientId/clientSecret
     * @param header header信息
     */
    public static String[] extractAndDecodeHeader(String header){
        String headerInfo = header.substring(AUTH_HEADER_PREFIX.length());
        byte[] decoded = Base64.getDecoder().decode(headerInfo);

        try {
            String token = new String(decoded, CommonConstant.CONTENT_ENCODE);
            int delimit = token.indexOf(AUTH_INFO_DELIMIT);

            if (delimit != -1) {
                return new String[]{token.substring(0, delimit), token.substring(delimit + 1)};
            }
        } catch (Exception e) {
            log.error("Failed to decode authentication token\r\n",e);
            throw new ErrorMsgException("Failed to decode authentication token");
        }
        return null;
    }

    /**
     * 从请求中获取clientId/clientSecret
     * @param request  请求
     * @return
     */
    public static String[] extractAndDecodeHeader(HttpServletRequest request) {
        String header = request.getHeader(AUTH_HEADER);

        if (header == null || header.startsWith(AUTH_HEADER_PREFIX)) {
            throw new ErrorMsgException(
                    "Failed to decode authentication token");
        }
        return extractAndDecodeHeader(header);
    }
}
