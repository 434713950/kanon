package com.github.kanon.generate.plugin.config;

import java.util.Properties;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/26
 */
public interface ConfigurationAdapter {

    boolean parse(Properties properties);
}
