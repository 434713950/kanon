package com.github.kanon.generate.plugin;

import com.github.kanon.generate.anno.CommonlyAnnoEnum;
import com.github.kanon.generate.plugin.context.BusinessGenerateContext;
import com.github.kanon.generate.plugin.generator.ControllerGeneratorAdapter;
import com.github.kanon.generate.plugin.generator.ServiceGeneratorAdapter;
import com.github.kanon.generate.util.AnnoAdjunctionUtil;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/25
 */
public class MybatisClientPlusPlugin extends PluginAdapter {

    protected BusinessGenerateContext businessGenerateContext;

    /**
     * entity对象生成
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        String tableComment = businessGenerateContext.setTableComment(introspectedTable);
        businessGenerateContext.getEntityGenerator().generate(topLevelClass,introspectedTable,tableComment);
        businessGenerateContext.setEntity(topLevelClass);
        return true;
    }

    /**
     * 属性上添加swagger注解
     * @param field
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return
     */
    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        Map<String,String> annoContentMap = new HashMap<>();
        annoContentMap.put("value","\""+introspectedColumn.getRemarks()+"\"");
        AnnoAdjunctionUtil.addFieldAnno(field,topLevelClass, CommonlyAnnoEnum.SWAGGER_API_MODEL_PROP,annoContentMap);
        return true;
    }

    /**
     * mapper类生成
     * @param interfaze
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        businessGenerateContext.getMapperGenerator().generate(interfaze,businessGenerateContext.getEntity());
        businessGenerateContext.setMapper(interfaze);
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        List<GeneratedJavaFile> generatedJavaFiles = new ArrayList<>();

        ServiceGeneratorAdapter serviceGenerator = businessGenerateContext.getServiceGenerator();
        if (serviceGenerator !=null) {
            //生成service接口
            GeneratedJavaFile iServiceJavaFile = serviceGenerator.generateInterface(businessGenerateContext.getEntity(),introspectedTable, context.getJavaFormatter());
            businessGenerateContext.setIService((Interface) iServiceJavaFile.getCompilationUnit());
            generatedJavaFiles.add(iServiceJavaFile);

            //生成service实现类
            GeneratedJavaFile serviceJavaFile = serviceGenerator.generateImpl(businessGenerateContext.getEntity(), businessGenerateContext.getMapper(), businessGenerateContext.getIService(),introspectedTable, context.getJavaFormatter());
            businessGenerateContext.setService((TopLevelClass) serviceJavaFile.getCompilationUnit());
            generatedJavaFiles.add(serviceJavaFile);
        }

        ControllerGeneratorAdapter controllerGenerator = businessGenerateContext.getControllerGenerator();
        if (controllerGenerator != null) {
            //生成controller
            GeneratedJavaFile controllerJavaFile =controllerGenerator.generate(businessGenerateContext.getEntity(), businessGenerateContext.getIService(), introspectedTable, context.getJavaFormatter(),businessGenerateContext.getTableComment());
            businessGenerateContext.setController((TopLevelClass) controllerJavaFile.getCompilationUnit());
            generatedJavaFiles.add(controllerJavaFile);
        }

        return generatedJavaFiles;
    }

    @Override
    public boolean validate(List<String> warnings) {
        businessGenerateContext = new BusinessGenerateContext();
        return businessGenerateContext.parseConfig(properties);
    }

    /**
     * 抑制Getter方法的生成
     * @param method
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return
     */
    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    /**
     * 抑制setter方法的生成
     * @param method
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return
     */
    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }


    /**
     * 抑制example的生成
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientCountByExampleMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientCountByExampleMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientDeleteByExampleMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientDeleteByExampleMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleSelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleSelectiveMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

}
