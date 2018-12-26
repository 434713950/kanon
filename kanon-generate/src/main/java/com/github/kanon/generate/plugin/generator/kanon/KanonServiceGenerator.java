package com.github.kanon.generate.plugin.generator.kanon;

import com.github.kanon.generate.plugin.constant.KanonPackage;
import com.github.kanon.generate.plugin.config.ServiceConfiguration;
import com.github.kanon.generate.plugin.generator.mybatisplus.MybatisPlusServiceGenerator;
import org.mybatis.generator.api.dom.java.*;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/26
 */
public class KanonServiceGenerator extends MybatisPlusServiceGenerator {

    public KanonServiceGenerator(ServiceConfiguration configuration) {
        super(configuration);
    }

    @Override
    protected void addInterfaceSuper(Interface service, TopLevelClass entity){
        FullyQualifiedJavaType mybatisPlusServiceType = new FullyQualifiedJavaType(KanonPackage.KANON_SERVICE);
        mybatisPlusServiceType.addTypeArgument(entity.getType());
        service.addSuperInterface(mybatisPlusServiceType);
        service.addImportedType(mybatisPlusServiceType);
    }

    @Override
    protected void addImplSuper(TopLevelClass service, TopLevelClass entity, Interface mapper, Interface iService){
        FullyQualifiedJavaType mybatisPlusServiceType = new FullyQualifiedJavaType(KanonPackage.KANON_SERVICE_IMPL);
        mybatisPlusServiceType.addTypeArgument(mapper.getType());
        mybatisPlusServiceType.addTypeArgument(entity.getType());
        service.setSuperClass(mybatisPlusServiceType);
        service.addImportedType(mybatisPlusServiceType);

        service.addSuperInterface(iService.getType());
        service.addImportedType(iService.getType());
    }
}
