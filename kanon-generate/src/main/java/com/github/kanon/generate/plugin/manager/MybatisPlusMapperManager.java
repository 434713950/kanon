package com.github.kanon.generate.plugin.manager;

import com.github.kanon.generate.plugin.MybatisPlusPackage;
import com.github.kanon.generate.plugin.config.MybatisPlusMapperConfiguration;
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
public class MybatisPlusMapperManager {

    public static void generateMapper(Interface interfaze, TopLevelClass entity, MybatisPlusMapperConfiguration configuration) {
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(MybatisPlusPackage.BASE_MAPPER_PACKAGE);
        type.addTypeArgument(entity.getType());
        interfaze.addSuperInterface(type);
        interfaze.addImportedType(type);

        CommentTagUtil.addUnifyComment(interfaze);
    }
}
