package com.github.kanon.common.base.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.github.tool.common.DateUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/17
 */
@Configuration
@ConditionalOnClass(com.alibaba.fastjson.JSON.class)
@ConditionalOnMissingBean(FastJsonHttpMessageConverter.class)
@ConditionalOnWebApplication
public class FastJsonAutoConfiguration {

    /**
     * http消息序列化技术替换
     * @return
     */
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        converter.setFastJsonConfig(fastjsonConfig());
        converter.setSupportedMediaTypes(getSupportedMediaType());
        return new HttpMessageConverters(converter);
    }

    /**
     * fastjson的配置
     */
    public FastJsonConfig fastjsonConfig() {
        FastJsonConfig fastJsonConfig = new FastJsonConfig();

        //序列化特征
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat
        );
        //设置默认的日期格式
        fastJsonConfig.setDateFormat(DateUtil.DATE_TIME_FORMAT);
        fastJsonConfig.setCharset(Charset.forName("utf-8"));
        initValueFilters(fastJsonConfig);
        return fastJsonConfig;
    }


    /**
     * 初始化value过滤器
     * 默认的valueFilter是把空的字段转化为空串
     */
    protected void initValueFilters(FastJsonConfig fastJsonConfig) {

        //为空的值转化为空串
        ValueFilter nullValueFilter = (object, name, value) -> {
            if (null == value) {
                return "";
            } else {
                return value;
            }
        };

        //为long的值转化为字符串，以防js丢失精度(js会丢失16位及以上的精度)
        ValueFilter longValueFilter = (object, name, value) -> {
            if (value instanceof Long) {
                return String.valueOf(value);
            } else {
                return value;
            }
        };

        fastJsonConfig.setSerializeFilters(nullValueFilter, longValueFilter);
    }

    /**
     * 支持的mediaType类型
     */
    public List<MediaType> getSupportedMediaType() {
        ArrayList<MediaType> mediaTypes = new ArrayList<>();

        mediaTypes.add(MediaType.APPLICATION_JSON);
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);

        //增加解析spring boot actuator结果的解析
        mediaTypes.add(MediaType.valueOf("application/vnd.spring-boot.actuator.v2+json"));
        return mediaTypes;
    }
}
