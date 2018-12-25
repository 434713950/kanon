package com.github.kanon.generate.plugin.manager;

import com.github.kanon.generate.anno.CommonlyAnnoEnum;
import com.github.kanon.generate.plugin.MybatisPlusPackage;
import com.github.kanon.generate.plugin.config.MybatisPlusServiceConfiguration;
import com.github.kanon.generate.util.AnnoAdjunctionUtil;
import com.github.kanon.generate.util.CommentTagUtil;
import com.github.kanon.generate.util.StringUtil;
import org.mybatis.generator.api.GeneratedJavaFile;
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
public class MybatisPlusServiceManager {

    private static final String IMPL_PATH_APPEND = "impl";

    public static GeneratedJavaFile generateInterface (TopLevelClass entity, JavaFormatter javaFormatter, MybatisPlusServiceConfiguration configuration){
        //接口shortName
        StringBuilder serviceInterfaceShortName = new StringBuilder()
                .append("I").append(StringUtil.firstUpperCase(entity.getType().getShortName())).append("Service");
        StringBuilder serviceInterfacePackage = new StringBuilder().append(configuration.getTargetPackage()).append(".").append(serviceInterfaceShortName);

        Interface service = new Interface(serviceInterfacePackage.toString());
        service.setVisibility(JavaVisibility.PUBLIC);

        FullyQualifiedJavaType mybatisPlusServiceType = new FullyQualifiedJavaType(MybatisPlusPackage.BASE_SERVICE);
        mybatisPlusServiceType.addTypeArgument(entity.getType());
        service.addSuperInterface(mybatisPlusServiceType);

        service.addImportedType(mybatisPlusServiceType);

        CommentTagUtil.addUnifyComment(service);

        return new GeneratedJavaFile(service,configuration.getTargetProject(),javaFormatter);
    }


    public static GeneratedJavaFile generatedImpl(TopLevelClass entity, Interface mapper, Interface iService, JavaFormatter javaFormatter,MybatisPlusServiceConfiguration configuration){
        StringBuilder serviceClassShortName = new StringBuilder().append(StringUtil.firstUpperCase(entity.getType().getShortName())).append("Service");
        StringBuilder serviceClassPackage = new StringBuilder().append(configuration.getTargetPackage())
                .append(".").append(IMPL_PATH_APPEND).append(".").append(serviceClassShortName);

        TopLevelClass service = new TopLevelClass(serviceClassPackage.toString());
        service.setVisibility(JavaVisibility.PUBLIC);

        FullyQualifiedJavaType mybatisPlusServiceType = new FullyQualifiedJavaType(MybatisPlusPackage.BASE_SERVICE_IMPL);
        mybatisPlusServiceType.addTypeArgument(mapper.getType());
        mybatisPlusServiceType.addTypeArgument(entity.getType());
        service.setSuperClass(mybatisPlusServiceType);
        service.addImportedType(mybatisPlusServiceType);

        service.addSuperInterface(iService.getType());
        service.addImportedType(iService.getType());

        //加注释
        CommentTagUtil.addUnifyComment(service);

        //加注解
        Map<String,String> annoContent = new HashMap<>();
        annoContent.put("value","\""+StringUtil.firstLowerCase(serviceClassShortName.toString())+"\"");
        AnnoAdjunctionUtil.addClassAnno(service, CommonlyAnnoEnum.SPRING_SERVICE,annoContent);

        return new GeneratedJavaFile(service,configuration.getTargetProject(),javaFormatter);
    }
}
