package com.github.kanon.generate.plugin.config;

import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/25
 */
public class MybatisPlusServiceConfiguration {

    private String targetPackage;

    private String targetProject;

    public boolean parse(Properties properties){
        targetPackage = properties.getProperty("service.targetPackage");
        targetProject = properties.getProperty("service.targetProject");
        return StringUtils.isNotEmpty(targetPackage) && StringUtils.isNotEmpty(targetProject);
    }

    public String getTargetPackage() {
        return targetPackage;
    }

    public String getTargetProject() {
        return targetProject;
    }


}
