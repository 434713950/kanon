package com.github.kanon.generate.plugin.generator.kanon;

import com.github.kanon.generate.plugin.constant.KanonPackage;
import com.github.kanon.generate.plugin.config.ControllerConfiguration;
import com.github.kanon.generate.plugin.generator.mybatisplus.MybatisPlusControllerGenerator;
import org.mybatis.generator.api.dom.java.*;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/26
 */
public class KanonControllerGenerator extends MybatisPlusControllerGenerator {

    public KanonControllerGenerator(ControllerConfiguration configuration) {
        super(configuration);
    }

    @Override
    protected void addSuper(TopLevelClass controller){
        FullyQualifiedJavaType baseControllerType = new FullyQualifiedJavaType(KanonPackage.KANON_BASE_CONTROLLER);
        controller.addSuperInterface(baseControllerType);
        controller.addImportedType(baseControllerType);
    }
}
