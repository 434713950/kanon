package com.github.kanon.gateway.component.fallback;

import com.github.kanon.common.constants.ServerConstantsConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @Author: PengCheng
 * @Description:  upms异常回调
 * @Date: 2018/9/12
 */
@Slf4j
@Component
public class UpmsFallbackProvider implements FallbackProvider {

    @Autowired
    private ServerConstantsConfiguration serverConstantsConfiguration;

    @Override
    public ClientHttpResponse fallbackResponse(Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() {
                return HttpStatus.SERVICE_UNAVAILABLE;
            }

            @Override
            public int getRawStatusCode() {
                return HttpStatus.SERVICE_UNAVAILABLE.value();
            }

            @Override
            public String getStatusText() {
                return HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase();
            }

            @Override
            public void close() {
            }

            @Override
            public InputStream getBody() {
                if (cause != null && cause.getMessage() != null) {
                    log.error("调用:{} 异常：{}", getRoute(), cause.getMessage());
                    return new ByteArrayInputStream(cause.getMessage().getBytes());
                } else {
                    log.error("调用:{} 异常：{}", getRoute(), "权限管理模块不可用");
                    return new ByteArrayInputStream("权限管理模块不可用".getBytes());
                }
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }

    /**
     * 路由对应服务名
     * @return
     */
    @Override
    public String getRoute() {
        return serverConstantsConfiguration.getUpmsServerName();
    }

    @Override
    public ClientHttpResponse fallbackResponse() {
        return fallbackResponse(null);
    }
}
