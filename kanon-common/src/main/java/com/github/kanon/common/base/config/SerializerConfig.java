package com.github.kanon.common.base.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>使用fastJson控制请求响应的参数</p>
 *
 * @author PengCheng
 * @date 2018/7/12
 */
@Configuration
public class SerializerConfig {

    public static Long BROWSER_LONG_LENGTH_LIMIT = 10000000000000000L;

    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.PrettyFormat);
        config.setSerializeFilters(new ValueFilter() {
            //由于浏览器会对long截取16位，因此当long类型长度超过16位时转成String类型
            @Override
            public Object process(Object object, String name, Object value) {
                if(value != null && value instanceof Long){
                    if (((Long)value).longValue()>=BROWSER_LONG_LENGTH_LIMIT){
                        value = value+"";
                    }
                }
                return value;
            }
        });
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        converter.setFastJsonConfig(config);
        converter.setSupportedMediaTypes(mediaTypes);
        return new HttpMessageConverters(converter);
    }
}
