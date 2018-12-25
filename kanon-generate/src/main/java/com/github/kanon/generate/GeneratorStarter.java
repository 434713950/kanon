package com.github.kanon.generate;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.internal.NullProgressCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/19
 */
public class GeneratorStarter {

    public static void main(String[] args) {
        try {
            List<String> warnings = new ArrayList<String>();

            //加载配置
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(Thread.currentThread().getContextClassLoader().getResourceAsStream("generatorConfig.xml"));

            //文件回调
            DefaultShellCallback callback = new DefaultShellCallback(true);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);

            //生成
            myBatisGenerator.generate(new NullProgressCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
