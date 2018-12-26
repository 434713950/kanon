package com.github.kanon.generate.plugin.generator;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.TopLevelClass;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/26
 */
public interface EntityGeneratorAdapter {

    /**
     *
     * @param topLevelClass         默认生成的entity对象
     * @param introspectedTable     连接的表数据
     * @param tableComment          表注释
     */
    void generate(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, String tableComment);
}
