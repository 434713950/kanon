package com.github.kanon.generate.plugin;

import com.github.kanon.generate.plugin.context.KanonGeneratorContext;

import java.util.List;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/25
 */
public class KanonClientPlusPlugin extends MybatisClientPlusPlugin {

    @Override
    public boolean validate(List<String> warnings) {
        businessGenerateContext = new KanonGeneratorContext();
        return  businessGenerateContext.parseConfig(properties);
    }

}
