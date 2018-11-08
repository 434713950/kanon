package com.github.kanon.common.base.config;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;

/**
 * <p>spring validation 文件读取位置配置</p>
 * 将validation文件和国际化文件合并
 *
 * @author PengCheng
 * @date 2018/11/06
 */
@Configuration
public class ValidationConfig {

    @Value("${spring.messages.basename:messages}")
    private String i18nProp;

    @Bean
    public Validator getValidator() {
        Validator validator = Validation.byDefaultProvider().
                configure().
                messageInterpolator(new ResourceBundleMessageInterpolator(new PlatformResourceBundleLocator(i18nProp))).
                buildValidatorFactory().getValidator();
        return validator;
    }
}
