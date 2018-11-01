package com.github.kanon.auth.config;

import com.github.kanon.common.constants.KanonSecurityConstants;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Map;

/**
 * @Author: PengCheng
 * @Description:    jwt授权令牌装换器
 * @Date: 2018/8/28
 */
public class KanonJwtAccessTokenConverter extends JwtAccessTokenConverter {

    /**
     * 转换授权令牌
     * @param token
     * @param authentication
     * @return
     */
    @Override
    public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        Map<String, Object> map = (Map<String, Object>) super.convertAccessToken(token, authentication);
        //加入授权信息
        map.put("license",KanonSecurityConstants.KANON_LICENSE);
        return map;
    }

    /**
     * 提取授权令牌
     * @param value
     * @param map
     * @return
     */
    @Override
    public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map) {
        return super.extractAccessToken(value, map);
    }

    /**
     * 提取认证信息
     * @param map
     * @return
     */
    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        return super.extractAuthentication(map);
    }
}
