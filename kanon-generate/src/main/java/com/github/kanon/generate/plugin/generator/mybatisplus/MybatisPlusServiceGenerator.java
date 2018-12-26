package com.github.kanon.generate.plugin.generator.mybatisplus;

import com.github.kanon.generate.anno.CommonlyAnnoEnum;
import com.github.kanon.generate.plugin.constant.MybatisPlusPackage;
import com.github.kanon.generate.plugin.config.ServiceConfiguration;
import com.github.kanon.generate.plugin.generator.ServiceGeneratorAdapter;
import com.github.kanon.generate.util.AnnoAdjunctionUtil;
import com.github.kanon.generate.util.CommentTagUtil;
import com.github.kanon.generate.util.StringUtil;
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
 * @date 2018/12/25
 */
public class MybatisPlusServiceGenerator implements ServiceGeneratorAdapter {

    protected ServiceConfiguration configuration;

    public MybatisPlusServiceGenerator(ServiceConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public GeneratedJavaFile generateInterface (TopLevelClass entity, IntrospectedTable introspectedTable, JavaFormatter javaFormatter){
        Interface service =createInterface(entity);
        addInterfaceSuper(service,entity);
        addInterfaceComment(service);
        return createFile(service,javaFormatter);
    }


    @Override
    public GeneratedJavaFile generateImpl(TopLevelClass entity, Interface mapper, Interface iService, IntrospectedTable introspectedTable, JavaFormatter javaFormatter){
        TopLevelClass service = createImpl(entity);
        addImplSuper(service,entity,mapper,iService);
        addImplComment(service);
        addImplAnno(service);
        return createFile(service,javaFormatter);
    }

    protected GeneratedJavaFile createFile(CompilationUnit unit, JavaFormatter javaFormatter){
        return new GeneratedJavaFile(unit,configuration.getTargetProject(),javaFormatter);
    }


    protected Interface createInterface(TopLevelClass entity){
        StringBuilder serviceInterfaceShortName = new StringBuilder()
                .append("I").append(StringUtil.firstUpperCase(entity.getType().getShortName())).append("Service");
        StringBuilder serviceInterfacePackage = new StringBuilder().append(configuration.getTargetPackage()).append(".").append(serviceInterfaceShortName);

        Interface service = new Interface(serviceInterfacePackage.toString());
        service.setVisibility(JavaVisibility.PUBLIC);
        return service;
    }

    protected void addInterfaceSuper(Interface service, TopLevelClass entity){
        FullyQualifiedJavaType mybatisPlusServiceType = new FullyQualifiedJavaType(MybatisPlusPackage.BASE_SERVICE);
        mybatisPlusServiceType.addTypeArgument(entity.getType());
        service.addSuperInterface(mybatisPlusServiceType);
        service.addImportedType(mybatisPlusServiceType);
    }

    protected void addInterfaceComment(Interface service){
        CommentTagUtil.addUnifyComment(service);
    }

    protected TopLevelClass createImpl(TopLevelClass entity){
        StringBuilder serviceClassShortName = new StringBuilder().append(StringUtil.firstUpperCase(entity.getType().getShortName())).append("Service");
        StringBuilder serviceClassPackage = new StringBuilder().append(configuration.getTargetPackage())
                .append(".").append("impl").append(".").append(serviceClassShortName);

        TopLevelClass service = new TopLevelClass(serviceClassPackage.toString());
        service.setVisibility(JavaVisibility.PUBLIC);
        return service;
    }

    protected void addImplSuper(TopLevelClass service, TopLevelClass entity, Interface mapper, Interface iService){
        FullyQualifiedJavaType mybatisPlusServiceType = new FullyQualifiedJavaType(MybatisPlusPackage.BASE_SERVICE_IMPL);
        mybatisPlusServiceType.addTypeArgument(mapper.getType());
        mybatisPlusServiceType.addTypeArgument(entity.getType());
        service.setSuperClass(mybatisPlusServiceType);
        service.addImportedType(mybatisPlusServiceType);

        service.addSuperInterface(iService.getType());
        service.addImportedType(iService.getType());
    }

    protected void addImplComment(TopLevelClass service){
        CommentTagUtil.addUnifyComment(service);
    }

    protected void addImplAnno(TopLevelClass service){
        //加注解
        Map<String,String> annoContent = new HashMap<>();
        annoContent.put("value","\""+StringUtil.firstLowerCase(service.getType().getShortName())+"\"");
        AnnoAdjunctionUtil.addClassAnno(service, CommonlyAnnoEnum.SPRING_SERVICE,annoContent);
    }
}
