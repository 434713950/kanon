package com.github.kanon.gateway.component.filter;

import com.github.kanon.common.constants.KanonSecurityConstants;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @Author: PengCheng
 * @Description:  接受过滤器.该过滤器主要任务是在请求头中添加用户角色信息
 * @Date: 2018/9/12
 */
@Component
public class AccessFilter extends  ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 与Servlet30WrapperFilter同级执行顺序
     * @return
     */
    @Override
    public int filterOrder() {
        return FilterConstants.SERVLET_30_WRAPPER_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        //向上下文设置起始时间参数
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.set("startTime", System.currentTimeMillis());

        //从security中抓取认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            RequestContext requestContext = RequestContext.getCurrentContext();
            requestContext.addZuulRequestHeader(KanonSecurityConstants.USER_HEADER, authentication.getName());

            StringBuilder role = new StringBuilder();
            Collection authorities = authentication.getAuthorities();
            if (authorities!=null && !authorities.isEmpty()) {
                authorities.forEach( authoritie ->{
                    role.append(authoritie).append(",");
                });
            }
            requestContext.addZuulRequestHeader(KanonSecurityConstants.ROLE_HEADER,  role.substring(0,role.length()-1));
        }
        return null;
    }
}
