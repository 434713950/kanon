package com.github.kanon.common.base.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.github.tool.common.DateUtil;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import javax.validation.Validation;
import javax.validation.Validator;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>通用配置</p>
 *
 * @author PengCheng
 * @date 2018/7/12
 */
@Configuration
@EnableCaching
public class CurrencyConfig {

    @Value("${spring.messages.basename:messages}")
    private String i18nProp;

    /**
     * 将validation文件和国际化文件合并
     * @return
     */
    @Bean
    public Validator getValidator() {
        Validator validator = Validation.byDefaultProvider().
                configure().
                messageInterpolator(new ResourceBundleMessageInterpolator(new PlatformResourceBundleLocator(i18nProp))).
                buildValidatorFactory().getValidator();
        return validator;
    }
}
