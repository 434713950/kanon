package com.github.kanon.generate.plugin.generator;

import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/26
 */
public interface MapperGeneratorAdapter {

    /**
     *
     * @param interfaze mapper接口
     * @param entity    与之对应的entity
     */
    void generate(Interface interfaze, TopLevelClass entity);
}
