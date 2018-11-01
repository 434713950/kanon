package com.github.kanon.common.utils.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/6/27
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        assertApplicationContext();
        return applicationContext;
    }

    public static <T> T getBean(String beanName) {
        assertApplicationContext();
        return (T) applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> requiredType) {
        assertApplicationContext();
        return applicationContext.getBean(requiredType);
    }

    /**
     * 动态注册bean
     * @param obj
     */
    public static void setBean(Object obj){
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
        applicationContext.getAutowireCapableBeanFactory().applyBeanPostProcessorsAfterInitialization(obj, obj.getClass().getName());
        beanFactory.registerSingleton(obj.getClass().getName(), obj);
    }

    private static void assertApplicationContext() {
        if (SpringUtil.applicationContext == null) {
            throw new RuntimeException("applicationContext is null,please check SpringContextHolder!");
        }
    }
}
