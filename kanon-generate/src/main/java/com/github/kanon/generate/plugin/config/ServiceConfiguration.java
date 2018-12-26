package com.github.kanon.generate.plugin.config;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/25
 */
public class ServiceConfiguration implements ConfigurationAdapter{

    @Getter
    private String targetPackage;

    @Getter
    private String targetProject;

    @Getter
    private boolean isNeed = false;

    @Override
    public boolean parse(Properties properties){
        targetPackage = properties.getProperty("service.targetPackage");
        targetProject = properties.getProperty("service.targetProject");
        String needGenerated =  properties.getProperty("service.need.generated");
        if (StringUtils.isNotEmpty(needGenerated)){
            isNeed = Boolean.parseBoolean(needGenerated);
        }
        return StringUtils.isNotEmpty(targetPackage) && StringUtils.isNotEmpty(targetProject);
    }
}
