package com.github.kanon.common.utils.net;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: peng_cheng
 * @Description: cookie工具
 */
public class CookieUtil {

	/**
	 * 设置Cookie(有过期)
	 * @param key 			Cookie名称
	 * @param value 		Cookie Value
	 * @param domain		Cookie作用域
	 * @param expires		过期时间（s）
	 * @param response	响应对象HttpServletResponse
	 */
	public static void setCookie(String key, String value, String domain,int expires,
								 HttpServletResponse response) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(expires);
		cookie.setPath("/");
		if (!StringUtils.isEmpty(domain)) {
			cookie.setDomain(domain);
		}
		response.addCookie(cookie);
	}

	/**
	 * 设置Cookie（不过期）
	 * @param key 			Cookie名称
	 * @param value 		Cookie Value
	 * @param domain		Cookie作用域
	 * @param response	响应对象HttpServletResponse
	 */
	public static void setCookie(String key, String value, String domain,
			HttpServletResponse response) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(-1);
		cookie.setPath("/");
		if (!StringUtils.isEmpty(domain)) {
			cookie.setDomain(domain);
		}
		response.addCookie(cookie);
	}

	/**
	 * 获取Cookie
	 * @param request 请求对象HttpServletRequest
	 * @param key		Cookie key
	 * @return			Cookie
	 */
	public static Cookie getCookie(HttpServletRequest request, String key){
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(key)) {
					cookie = cookies[i];
				}
			}
		}
		return cookie;
	}

	/**
	 * 清除cookie
	 * @param key			cookie key
	 * @param request		请求实体
	 * @param response	响应实体
	 */
	public static void deleteCookie(String key, HttpServletRequest request,
			HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(key)) {
					cookie.setValue("");
					cookie.setMaxAge(0);
					cookie.setPath("/");
					response.addCookie(cookie);
				}
			}
		}
	}
}
