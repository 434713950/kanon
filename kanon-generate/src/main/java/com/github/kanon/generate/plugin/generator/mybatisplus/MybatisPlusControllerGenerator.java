package com.github.kanon.generate.plugin.generator.mybatisplus;

import com.github.kanon.generate.anno.CommonlyAnnoEnum;
import com.github.kanon.generate.plugin.config.ControllerConfiguration;
import com.github.kanon.generate.plugin.generator.ControllerGeneratorAdapter;
import com.github.kanon.generate.util.AnnoAdjunctionUtil;
import com.github.kanon.generate.util.StringUtil;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.JavaFormatter;
import org.mybatis.generator.api.dom.java.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/26
 */
public class MybatisPlusControllerGenerator implements ControllerGeneratorAdapter {

    protected ControllerConfiguration configuration;

    public MybatisPlusControllerGenerator(ControllerConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public GeneratedJavaFile generate(TopLevelClass entity, Interface iService, IntrospectedTable introspectedTable, JavaFormatter javaFormatter, String tableComment) {
        TopLevelClass controller = createController(entity);
        addField(controller,iService);

        addControllerAnno(controller,introspectedTable,tableComment);
        addSuper(controller);
        return createFile(controller,javaFormatter);
    }

    protected void addSuper(TopLevelClass controller){}

    protected TopLevelClass createController(TopLevelClass entity){
        StringBuilder classShortName = new StringBuilder().append(StringUtil.firstUpperCase(entity.getType().getShortName())).append("Controller");
        StringBuilder classPackage = new StringBuilder().append(configuration.getTargetPackage())
                .append(".").append(classShortName);

        TopLevelClass controller = new TopLevelClass(classPackage.toString());
        controller.setVisibility(JavaVisibility.PUBLIC);
        return controller;
    }

    protected void addField(TopLevelClass controller, Interface iService){
        Field field = new Field(StringUtil.firstLowerCase(iService.getType().getShortName().substring(1)),iService.getType());
        field.setVisibility(JavaVisibility.PRIVATE);
        AnnoAdjunctionUtil.addFieldAnno(field,controller, CommonlyAnnoEnum.SPRING_AUTOWIRED);
        controller.addField(field);
        controller.addImportedType(iService.getType());
    }

    protected void addControllerAnno(TopLevelClass controller, IntrospectedTable introspectedTable, String tableComment){
        AnnoAdjunctionUtil.addClassAnno(controller,CommonlyAnnoEnum.SPRING_CONTROLLER);
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();

        Map<String,String> annoContent = new HashMap<>();

        annoContent.put("value","\""+StringUtil.firstLowerCase(table.getDomainObjectName())+"\"");
        AnnoAdjunctionUtil.addClassAnno(controller,CommonlyAnnoEnum.SPRING_REQUEST_MAPPING,annoContent);
        annoContent.clear();

        annoContent.put("description","\""+tableComment+"\"");
        annoContent.put("tags","\""+tableComment+"\"");
        AnnoAdjunctionUtil.addClassAnno(controller, CommonlyAnnoEnum.SWAGGER_API,annoContent);
        annoContent.clear();
    }

    protected GeneratedJavaFile createFile(CompilationUnit unit, JavaFormatter javaFormatter){
        return new GeneratedJavaFile(unit,configuration.getTargetProject(),javaFormatter);
    }
}
