package com.github.kanon.generate.plugin.context;

import com.github.kanon.generate.plugin.config.ControllerConfiguration;
import com.github.kanon.generate.plugin.config.ServiceConfiguration;
import com.github.kanon.generate.plugin.generator.kanon.KanonControllerGenerator;
import com.github.kanon.generate.plugin.generator.kanon.KanonServiceGenerator;
import com.github.kanon.generate.plugin.generator.mybatisplus.MybatisPlusEntityGenerator;
import com.github.kanon.generate.plugin.generator.mybatisplus.MybatisPlusMapperGenerator;
import lombok.Data;

import java.util.Properties;

/**
 * <p>kanon 生成器的上下文</p>
 *
 * @author PengCheng
 * @date 2018/12/26
 */
@Data
public class KanonGeneratorContext extends BusinessGenerateContext{

    @Override
    public boolean parseConfig(Properties properties) {
        entityGenerator = new MybatisPlusEntityGenerator();
        mapperGenerator = new MybatisPlusMapperGenerator();

        ServiceConfiguration serviceConfiguration = new ServiceConfiguration();
        serviceConfiguration.parse(properties);
        if (serviceConfiguration.isNeed()) {
            serviceGenerator = new KanonServiceGenerator(serviceConfiguration);

            ControllerConfiguration controllerConfiguration = new ControllerConfiguration();
            controllerConfiguration.parse(properties);
            if (controllerConfiguration.isNeed()){
                controllerGenerator = new KanonControllerGenerator(controllerConfiguration);
            }
        }
        return true;
    }
}
