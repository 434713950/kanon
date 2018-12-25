package com.github.kanon.generate.comment;

import com.github.kanon.generate.util.DateUtil;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.PropertyRegistry;

import java.util.List;
import java.util.Properties;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

/**
 * <p>注释生成器</p>
 *
 * @author PengCheng
 * @date 2018/12/17
 */
public class KanonCommentGenerator implements CommentGenerator {

    private Properties properties;

    /**
     *  是否禁用注释日期
     */
    private boolean suppressDate;

    /**
     *  是否禁用所有的注释
     */
    private boolean suppressAllComments;

    public KanonCommentGenerator() {
        super();
        this.properties = new Properties();
        this.suppressDate = false;
        this.suppressAllComments = false;
    }

    /**
     * 加载对应的配置
     * @param properties
     */
    @Override
    public void addConfigurationProperties(Properties properties) {
        //加载配置文件
        this.properties.putAll(properties);

        this.suppressDate = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));

        this.suppressAllComments = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));

    }

    /**
     * 属性注解
     * @param field
     * @param introspectedTable
     * @param introspectedColumn
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        field.addJavaDocLine("/**");
        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append(introspectedColumn.getRemarks());
        field.addJavaDocLine(sb.toString());
        field.addJavaDocLine(" */");

    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        field.addJavaDocLine("/**");
        field.addJavaDocLine(" * ");
        field.addJavaDocLine(" */");
    }

    /**
     * 添加类的注释
     * @param innerClass
     * @param introspectedTable
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

        innerClass.addJavaDocLine("/**");
        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable().getAlias());
        innerClass.addJavaDocLine(sb.toString());
        JavaCommentTag.addJavaAuthorTag(innerClass);
        if (!suppressDate) {
            JavaCommentTag.addJavaDateTag(innerClass);
        }
        innerClass.addJavaDocLine(" */");
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        addClassComment(innerClass,introspectedTable);
    }

    /**
     * 为枚举添加注释
     */
    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();

        innerEnum.addJavaDocLine("/**");
        innerEnum.addJavaDocLine(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerEnum.addJavaDocLine(sb.toString());
        JavaCommentTag.addJavaAuthorTag(innerEnum);
        if (!suppressDate) {
            JavaCommentTag.addJavaDateTag(innerEnum);
        }
        innerEnum.addJavaDocLine(" */");

    }

    /**
     * getter 方法注释(考虑到使用lombok,无需添加)
     * @param method
     * @param introspectedTable
     * @param introspectedColumn
     */
    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        //考虑到使用lombok,无需添加
        return;
    }

    /**
     * setter 方法注释
     * @param method
     * @param introspectedTable
     * @param introspectedColumn
     */
    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        //考虑到使用lombok,无需添加
        return;
    }

    /**
     * 生成方法的注释
     * @param method
     * @param introspectedTable
     */
    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * ");
        //获取变量加注释
        List<Parameter> parameters = method.getParameters();
        if (parameters!=null && !parameters.isEmpty()){
            for (Parameter parameter : parameters){
                JavaCommentTag.addJavaTag(method, JavaCommentTag.PARAM,parameter.getName());
            }
        }
        if (method.getReturnType()!=null){
            JavaCommentTag.addJavaTag(method, JavaCommentTag.RETURN,method.getReturnType().getFullyQualifiedName());
        }
        method.addJavaDocLine(" */");
    }

    /**
     * 文件注释
     * @param compilationUnit
     */
    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        //不需要
        return;
    }

    /**
     * xml文件中的注释
     * @param xmlElement
     */
    @Override
    public void addComment(XmlElement xmlElement) {
        if (suppressAllComments) {
            return;
        }

        xmlElement.addElement(new TextElement("<!--"));

        StringBuilder sb = new StringBuilder();
        sb.append("  WARNING - AUTO GENERATE BY KANON ");
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        xmlElement.addElement(new TextElement(sb.toString()));
        xmlElement.addElement(new TextElement( DateUtil.dateTimeFormat(DateUtil.getCurrentSystemTime())));
        xmlElement.addElement(new TextElement(" -->"));
    }

    @Override
    public void addRootComment(XmlElement xmlElement) {
        // add no document level comments by default
        return;
    }


}
