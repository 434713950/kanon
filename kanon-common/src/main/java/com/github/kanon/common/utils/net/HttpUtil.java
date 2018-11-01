package com.github.kanon.common.utils.net;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/9/13
 */
public class HttpUtil {

    /**
     * 获取请求的IP地址,该方法通用于反向代理的情形
     * @param request               请求
     * @param otherIpHeaderNames    需要多加的ip请求头
     * @return
     */
    public static String getRequestIPAddress(HttpServletRequest request,String... otherIpHeaderNames){
        List<String> ipHeaderNames = Arrays.asList("x-forwarded-for","Proxy-Client-IP","WL-Proxy-Client-IP","X-Real-IP","HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR");
        if (ArrayUtils.isNotEmpty(otherIpHeaderNames)){
            ipHeaderNames.addAll(Arrays.asList(otherIpHeaderNames));
        }

        for (String ipHeaderName:ipHeaderNames){
            String ip = request.getHeader(ipHeaderName);
            if (!isUnknow(ip)){
                return getMultistageReverseProxyIp(ip);
            }
        }
        return getMultistageReverseProxyIp(request.getRemoteAddr());
    }

    /**
     * 从多级反向代理中获得第一个非unknown IP地址
     * @param ip 获得的IP地址
     * @return 第一个非unknown IP地址
     */
    public static String getMultistageReverseProxyIp(String ip) {
        // 多级反向代理检测
        if (ip != null && ip.indexOf(",") > 0) {
            final String[] ips = ip.trim().split(",");
            for (String subIp : ips) {
                if (false == isUnknow(subIp)) {
                    ip = subIp;
                    break;
                }
            }
        }
        return ip;
    }

    /**
     * 判断字符串是否是未知的，该方法常用于http协议头中的校验
     * @param checkStr  被检测的字符串
     * @return
     */
    public static boolean isUnknow(String checkStr){
        return StringUtils.isEmpty(checkStr) || checkStr.equals("unknown");
    }
}
