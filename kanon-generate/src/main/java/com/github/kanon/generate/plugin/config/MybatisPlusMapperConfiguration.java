package com.github.kanon.generate.plugin.config;

import com.github.kanon.generate.plugin.MybatisPlusPackage;
import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/25
 */
public class MybatisPlusMapperConfiguration {

    private String superClass;

    public String getSuperClass() {
        return superClass;
    }

    public boolean parse(Properties properties){
        superClass =  properties.getProperty("mapper.superClass");
        if (StringUtils.isEmpty(superClass)){
            superClass = MybatisPlusPackage.BASE_MAPPER_PACKAGE;
        }
        return true;
    }
}
