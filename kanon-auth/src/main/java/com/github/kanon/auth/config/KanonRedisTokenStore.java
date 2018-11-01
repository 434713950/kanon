package com.github.kanon.auth.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: PengCheng
 * @Description:   自定义redis模式的token存储方式,仿InMemoryTokenStore实现.该自定义存储模式相较于框架提供的RedisTokenStore好处在于该模式使用了redisTemplate,而框架使用的则是redisFactory,本模式扩展更强
 * @Date: 2018/8/28
 */
@Slf4j
public class KanonRedisTokenStore implements TokenStore {

    private static final String ACCESS = "access:";
    private static final String AUTH_TO_ACCESS = "auth_to_access:";
    private static final String AUTH = "auth:";
    private static final String REFRESH_AUTH = "refresh_auth:";
    private static final String ACCESS_TO_REFRESH = "access_to_refresh:";
    private static final String REFRESH = "refresh:";
    private static final String REFRESH_TO_ACCESS = "refresh_to_access:";
    private static final String CLIENT_ID_TO_ACCESS = "client_id_to_access:";
    private static final String USER_NAME_TO_ACCESS = "user_name_to_access:";

    @Getter@Setter
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 令牌key生成器
     */
    @Setter
    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

    public KanonRedisTokenStore() {
    }

    public KanonRedisTokenStore(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 通过认证信息生成授权令牌
     * @param authentication
     * @return
     */
    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        String key = authenticationKeyGenerator.extractKey(authentication);

        //先从redis中获取token,如果不存在则先调用token生成器金鑫鑫token的生成
        OAuth2AccessToken accessToken = (OAuth2AccessToken) redisTemplate.opsForValue().get(AUTH_TO_ACCESS + key);
        if (accessToken != null
                && !key.equals(authenticationKeyGenerator.extractKey(readAuthentication(accessToken.getValue())))) {
            storeAccessToken(accessToken, authentication);
        }
        return accessToken;
    }

    /**
     * 从授权令牌中读取认证信息
     * @param token
     * @return
     */
    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return readAuthentication(token.getValue());
    }

    /**
     * 从授权令牌中读取认证信息
     * @param token
     * @return
     */
    @Override
    public OAuth2Authentication readAuthentication(String token) {
        return (OAuth2Authentication) this.redisTemplate.opsForValue().get(AUTH + token);
    }

    /**
     * 从刷新令牌中读取认证信息
     * @param token
     * @return
     */
    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return readAuthenticationForRefreshToken(token.getValue());
    }

    /**
     * 从刷新令牌中读取认证信息
     * @param token
     * @return
     */
    public OAuth2Authentication readAuthenticationForRefreshToken(String token) {
        return (OAuth2Authentication) this.redisTemplate.opsForValue().get(REFRESH_AUTH + token);
    }

    /**
     * 存储授权令牌
     * @param token
     * @param authentication
     */
    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        this.redisTemplate.opsForValue().set(ACCESS + token.getValue(), token);
        this.redisTemplate.opsForValue().set(AUTH + token.getValue(), authentication);
        this.redisTemplate.opsForValue().set(AUTH_TO_ACCESS + authenticationKeyGenerator.extractKey(authentication), token);
        if (!authentication.isClientOnly()) {
            redisTemplate.opsForList().rightPush(USER_NAME_TO_ACCESS + getApprovalKey(authentication), token);
        }

        redisTemplate.opsForList().rightPush(CLIENT_ID_TO_ACCESS + authentication.getOAuth2Request().getClientId(), token);

        /**
         * 当token设置过期参数后，给每个缓存参数设置过期时间
         * 在InMemoryTokenStore中是使用延时队列的SIZE做相关数据清除
         */
        if (token.getExpiration() != null) {
            int seconds = token.getExpiresIn();
            redisTemplate.expire(ACCESS + token.getValue(), seconds, TimeUnit.SECONDS);
            redisTemplate.expire(AUTH + token.getValue(), seconds, TimeUnit.SECONDS);

            redisTemplate.expire(AUTH_TO_ACCESS + authenticationKeyGenerator.extractKey(authentication), seconds, TimeUnit.SECONDS);
            redisTemplate.expire(CLIENT_ID_TO_ACCESS + authentication.getOAuth2Request().getClientId(), seconds, TimeUnit.SECONDS);
            redisTemplate.expire(USER_NAME_TO_ACCESS + getApprovalKey(authentication), seconds, TimeUnit.SECONDS);
        }
        if (token.getRefreshToken() != null && token.getRefreshToken().getValue() != null) {
            this.redisTemplate.opsForValue().set(REFRESH_TO_ACCESS + token.getRefreshToken().getValue(), token.getValue());
            this.redisTemplate.opsForValue().set(ACCESS_TO_REFRESH + token.getValue(), token.getRefreshToken().getValue());
        }
    }

    private String getApprovalKey(OAuth2Authentication authentication) {
        String userName = authentication.getUserAuthentication() == null ? "" : authentication.getUserAuthentication().getName();
        return getApprovalKey(authentication.getOAuth2Request().getClientId(), userName);
    }

    private String getApprovalKey(String clientId, String userName) {
        return clientId + (userName == null ? "" : ":" + userName);
    }

    /**
     * 移除授权令牌
     * @param accessToken
     */
    @Override
    public void removeAccessToken(OAuth2AccessToken accessToken) {
        removeAccessToken(accessToken.getValue());
    }

    /**
     * 移除授权令牌
     * @param tokenValue
     */
    public void removeAccessToken(String tokenValue) {
        //OAuth2AccessToken removed = this.accessTokenStore.remove(tokenValue);
        OAuth2AccessToken removed = (OAuth2AccessToken) this.redisTemplate.opsForValue().get(ACCESS + tokenValue);

        this.redisTemplate.delete(ACCESS + tokenValue);
        this.redisTemplate.delete(ACCESS_TO_REFRESH + tokenValue);

        // 这里不要移除刷新对象缓存 - 它是有caller来执行的
        OAuth2Authentication authentication = (OAuth2Authentication) this.redisTemplate.opsForValue().get(AUTH + tokenValue);
        this.redisTemplate.delete(AUTH + tokenValue);

        if (authentication != null) {
            this.redisTemplate.delete(AUTH_TO_ACCESS + authenticationKeyGenerator.extractKey(authentication));
            String clientId = authentication.getOAuth2Request().getClientId();
            redisTemplate.opsForList().leftPop(USER_NAME_TO_ACCESS + getApprovalKey(clientId, authentication.getName()));

            //tokens = this.clientIdToAccessTokenStore.get(clientId);
            //			if (tokens != null) {
            //				tokens.remove(removed);
            //			}
            redisTemplate.opsForList().leftPop(CLIENT_ID_TO_ACCESS + clientId);

            this.redisTemplate.delete(AUTH_TO_ACCESS + authenticationKeyGenerator.extractKey(authentication));
        }
    }

    /**
     * 读取授权令牌
     * @param tokenValue
     * @return
     */
    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        return (OAuth2AccessToken) this.redisTemplate.opsForValue().get(ACCESS + tokenValue);
    }

    /**
     * 存储刷新令牌
     * @param refreshToken
     * @param authentication
     */
    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        this.redisTemplate.opsForValue().set(REFRESH + refreshToken.getValue(), refreshToken);
        this.redisTemplate.opsForValue().set(REFRESH_AUTH + refreshToken.getValue(), authentication);
    }

    /**
     * 读取刷新令牌
     * @param tokenValue
     * @return
     */
    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        return (OAuth2RefreshToken) this.redisTemplate.opsForValue().get(REFRESH + tokenValue);
    }

    /**
     * 移除刷新令牌
     * @param refreshToken
     */
    @Override
    public void removeRefreshToken(OAuth2RefreshToken refreshToken) {
        removeRefreshToken(refreshToken.getValue());
    }

    public void removeRefreshToken(String tokenValue) {
        this.redisTemplate.delete(REFRESH + tokenValue);
        this.redisTemplate.delete(REFRESH_AUTH + tokenValue);
        this.redisTemplate.delete(REFRESH_TO_ACCESS + tokenValue);
    }

    /**
     * 通过刷新令牌来移除授权令牌
     * @param refreshToken
     */
    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        removeAccessTokenUsingRefreshToken(refreshToken.getValue());
    }

    /**
     * 通过刷新令牌来移除授权令牌
     * @param refreshToken
     */
    private void removeAccessTokenUsingRefreshToken(String refreshToken) {
        String token = (String) this.redisTemplate.opsForValue().get(REFRESH_TO_ACCESS + refreshToken);
        if (token != null) {
            redisTemplate.delete(ACCESS + token);
        }
    }

    /**
     * 通过用户名和客户端Id来查询令牌
     * @param clientId
     * @param userName
     * @return
     */
    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        List<Object> result = redisTemplate.opsForList().range(USER_NAME_TO_ACCESS + getApprovalKey(clientId, userName), 0, -1);

        if (result == null || result.size() == 0) {
            return Collections.emptySet();
        }
        List<OAuth2AccessToken> accessTokens = new ArrayList<>(result.size());

        for (Iterator<Object> it = result.iterator(); it.hasNext(); ) {
            OAuth2AccessToken accessToken = (OAuth2AccessToken) it.next();
            accessTokens.add(accessToken);
        }

        return Collections.unmodifiableCollection(accessTokens);
    }

    /**
     * 通过客户端Id来查询令牌
     * @param clientId
     * @return
     */
    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        List<Object> result = redisTemplate.opsForList().range((CLIENT_ID_TO_ACCESS + clientId), 0, -1);

        if (result == null || result.size() == 0) {
            return Collections.emptySet();
        }
        List<OAuth2AccessToken> accessTokens = new ArrayList<>(result.size());
        for (Iterator<Object> it = result.iterator(); it.hasNext(); ) {
            OAuth2AccessToken accessToken = (OAuth2AccessToken) it.next();
            accessTokens.add(accessToken);
        }

        return Collections.unmodifiableCollection(accessTokens);
    }
}