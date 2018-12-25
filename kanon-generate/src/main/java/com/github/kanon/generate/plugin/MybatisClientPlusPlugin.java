package com.github.kanon.generate.plugin;

import com.github.kanon.generate.anno.CommonlyAnnoEnum;
import com.github.kanon.generate.plugin.config.MybatisPlusMapperConfiguration;
import com.github.kanon.generate.plugin.config.MybatisPlusServiceConfiguration;
import com.github.kanon.generate.plugin.manager.MybatisPlusEntityManager;
import com.github.kanon.generate.plugin.manager.MybatisPlusMapperManager;
import com.github.kanon.generate.plugin.manager.MybatisPlusServiceManager;
import com.github.kanon.generate.util.AnnoAdjunctionUtil;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.DefaultJavaFormatter;
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

    /**
     * 实体对象
     */
    protected TopLevelClass entity;

    /**
     * mapper对象
     */
    protected Interface mapper;

    /**
     * service接口
     */
    protected Interface iService;

    /**
     * service实现
     */
    protected TopLevelClass service;


    protected MybatisPlusMapperConfiguration mapperConfiguration;

    protected MybatisPlusServiceConfiguration serviceConfiguration;

    /**
     * entity对象生成
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        MybatisPlusEntityManager.generateEntity(topLevelClass,introspectedTable,context);
        this.entity = topLevelClass;
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
        MybatisPlusMapperManager.generateMapper(interfaze,entity,mapperConfiguration);
        this.mapper = interfaze;
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        List<GeneratedJavaFile> generatedJavaFiles = new ArrayList<>();

        //生成service接口
        GeneratedJavaFile iServiceJavaFile =  MybatisPlusServiceManager.generateInterface(entity,new DefaultJavaFormatter(),serviceConfiguration);
        iService = (Interface) iServiceJavaFile.getCompilationUnit();
        generatedJavaFiles.add(iServiceJavaFile);

        //生成service实现类
        GeneratedJavaFile serviceJavaFile = MybatisPlusServiceManager.generatedImpl(entity,mapper,iService,new DefaultJavaFormatter(),serviceConfiguration);
        service = (TopLevelClass) serviceJavaFile.getCompilationUnit();
        generatedJavaFiles.add(serviceJavaFile);

        return generatedJavaFiles;
    }

    @Override
    public boolean validate(List<String> warnings) {
        mapperConfiguration = new MybatisPlusMapperConfiguration();
        serviceConfiguration = new MybatisPlusServiceConfiguration();
        return mapperConfiguration.parse(properties) && serviceConfiguration.parse(properties);
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
