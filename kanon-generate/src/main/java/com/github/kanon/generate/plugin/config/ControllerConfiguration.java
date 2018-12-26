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
public class ControllerConfiguration implements ConfigurationAdapter{

    @Getter
    private String targetPackage;

    @Getter
    private String targetProject;

    @Getter
    private boolean isNeed;

    @Override
    public boolean parse(Properties properties){
        targetPackage = properties.getProperty("controller.targetPackage");
        targetProject = properties.getProperty("controller.targetProject");
        String needGenerated =  properties.getProperty("controller.need.generated");
        if (StringUtils.isNotEmpty(needGenerated)){
            isNeed = Boolean.parseBoolean(needGenerated);
        }
        return StringUtils.isNotEmpty(targetPackage) && StringUtils.isNotEmpty(targetProject);
    }
}
