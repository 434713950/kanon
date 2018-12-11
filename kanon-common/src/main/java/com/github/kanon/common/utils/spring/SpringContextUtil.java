package com.github.kanon.common.utils.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * <p></p>
 *  该方法无法用在项目启动时加载bean
 * @author PengCheng
 * @date 2018/12/6
 */
public class SpringContextUtil {

    public static Object getBean(String name){
      return  getCurrentApplication().getBean(name);
    }

    public static <T> T  getBean(Class<T> beanClass){
        return  getCurrentApplication().getBean(beanClass);
    }

    public static ApplicationContext getCurrentApplication(){
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        return wac;
    }
}
