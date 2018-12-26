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
public interface ControllerGeneratorAdapter {

    GeneratedJavaFile generate(TopLevelClass entity, Interface iService, IntrospectedTable introspectedTable, JavaFormatter javaFormatter,String tableComment);
}
