package com.github.kanon.generate.plugin.generator.mybatisplus;

import com.github.kanon.generate.anno.CommonlyAnnoEnum;
import com.github.kanon.generate.comment.JavaCommentTag;
import com.github.kanon.generate.plugin.constant.MybatisPlusPackage;
import com.github.kanon.generate.plugin.generator.EntityGeneratorAdapter;
import com.github.kanon.generate.util.AnnoAdjunctionUtil;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.HashMap;
import java.util.Map;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/25
 */
public class MybatisPlusEntityGenerator implements EntityGeneratorAdapter {

    @Override
    public void generate(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, String tableComment){
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();

        //加注释
        addClassComment(topLevelClass,table,tableComment);
        //加注解
        addClassAnno(topLevelClass,table,tableComment);

        addSuper(topLevelClass);
    }

    /**
     * 添加父类
     * @param topLevelClass
     */
    protected void addSuper(TopLevelClass topLevelClass){
        topLevelClass.addImportedType(MybatisPlusPackage.MODEL_PACKAGE);
        FullyQualifiedJavaType modelType = new FullyQualifiedJavaType(MybatisPlusPackage.MODEL_PACKAGE);
        modelType.addTypeArgument(topLevelClass.getType());
        topLevelClass.setSuperClass(modelType);

        //添加重写方法
        Method method = new Method("pkVal");
        method.setVisibility(JavaVisibility.PROTECTED);
        //默认返回null
        method.addBodyLine("return null;");

        //返回参数
        topLevelClass.addImportedType("java.io.Serializable");
        method.setReturnType(new FullyQualifiedJavaType("Serializable"));
        //重写注解
        method.addAnnotation("@Override");
        topLevelClass.addMethod(method);
    }

    /**
     * 添加pojo类表注释
     * @param topLevelClass
     * @param table
     */
    protected void addClassComment(TopLevelClass topLevelClass, FullyQualifiedTable table, String tableComment) {
        topLevelClass.addJavaDocLine("/**");
        if(StringUtility.stringHasValue(tableComment)) {
            topLevelClass.addJavaDocLine(" * <p>" + tableComment + "<p/>");
        }
        topLevelClass.addJavaDocLine(" * <p>" + table.toString() + "<p/>");
        JavaCommentTag.addJavaAuthorTag(topLevelClass);
        JavaCommentTag.addJavaDateTag(topLevelClass);
        topLevelClass.addJavaDocLine(" */");
    }

    /**
     * 添加通用的注解
     * @param topLevelClass
     * @param table
     */
    protected void addClassAnno(TopLevelClass topLevelClass, FullyQualifiedTable table, String tableComment){
        AnnoAdjunctionUtil.addClassAnno(topLevelClass, CommonlyAnnoEnum.LOMBOK_DATA);

        Map<String,String> annoContentMap = new HashMap<>(16);
        annoContentMap.put("chain","true");
        AnnoAdjunctionUtil.addClassAnno(topLevelClass, CommonlyAnnoEnum.LOMBOK_ACCESSOR,annoContentMap);

        annoContentMap.clear();
        annoContentMap.put("value","\""+table.toString()+"\"");
        AnnoAdjunctionUtil.addClassAnno(topLevelClass, CommonlyAnnoEnum.MYBATIS_TABLE,annoContentMap);

        annoContentMap.clear();
        annoContentMap.put("value","\""+topLevelClass.getType().getShortName()+"\"");
        annoContentMap.put("description","\""+tableComment+"\"");
        AnnoAdjunctionUtil.addClassAnno(topLevelClass, CommonlyAnnoEnum.SWAGGER_API_MODEL,annoContentMap);
    }
}
