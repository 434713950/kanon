package com.github.kanon.auth.config;

import com.github.kanon.common.constants.KanonSecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: PengCheng
 * @Description:    认证服务配置
 * @Date: 2018/8/28
 */
@Configuration
//认证器最后加载
@Order(Integer.MIN_VALUE)
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${auth.sign.key:kanon}")
    private String signKey;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 数据库模式配置
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //配置数据库存储参数认证,需要创建对应的表单
        JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        clients.withClientDetails(clientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                //token存入redis缓存中
                .tokenStore(redisTokenStore())
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenEnhancer(tokenEnhancer())
                //用户信息服务
                .userDetailsService(userDetailsService)
                .authenticationManager(authenticationManager);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .allowFormAuthenticationForClients()
                .tokenKeyAccess("isAuthenticated()")
                .checkTokenAccess("permitAll()");
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        KanonJwtAccessTokenConverter jwtAccessTokenConverter = new KanonJwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(signKey);
        return jwtAccessTokenConverter;
    }

    /**
     * tokenstore 定制化处理
     * 这里使用自定义的redis令牌存储做处理
     * @return TokenStore
     */
    @Bean
    public TokenStore redisTokenStore() {
        KanonRedisTokenStore tokenStore = new KanonRedisTokenStore(redisTemplate);
        return tokenStore;
    }

    /**
     * token增强器
     * @return
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            final Map<String, Object> additionalInfo = new HashMap<>(1);
            additionalInfo.put("license", KanonSecurityConstants.KANON_LICENSE);
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }
}
