package com.github.kanon.generate.plugin.generator.mybatisplus;

import com.github.kanon.generate.plugin.constant.MybatisPlusPackage;
import com.github.kanon.generate.plugin.generator.MapperGeneratorAdapter;
import com.github.kanon.generate.util.CommentTagUtil;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/25
 */
public class MybatisPlusMapperGenerator implements MapperGeneratorAdapter {

    @Override
    public void generate(Interface interfaze, TopLevelClass entity) {
        addSuper(interfaze,entity);
        addComment(interfaze);
    }

    protected void addSuper(Interface interfaze, TopLevelClass entity){
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(MybatisPlusPackage.BASE_MAPPER_PACKAGE);
        type.addTypeArgument(entity.getType());
        interfaze.addSuperInterface(type);
        interfaze.addImportedType(type);
    }

    protected void addComment(Interface interfaze){
        CommentTagUtil.addUnifyComment(interfaze);
    }
}
