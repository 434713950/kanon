package com.github.kanon.generate.plugin.generator;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.JavaFormatter;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/26
 */
public interface ServiceGeneratorAdapter {

    /**
     *
     * @param entity    当前service对应的实例
     * @return
     */
    GeneratedJavaFile generateInterface (TopLevelClass entity, IntrospectedTable introspectedTable,JavaFormatter javaFormatter);


    /**
     *
     * @param entity    当前service对应的实例
     * @param mapper    当前service对应的mapper
     * @param iService  当前service对应的service接口层
     * @return
     */
    GeneratedJavaFile generateImpl (TopLevelClass entity, Interface mapper, Interface iService, IntrospectedTable introspectedTable,JavaFormatter javaFormatter);
}
