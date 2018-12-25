package com.github.kanon.generate.plugin;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/25
 */
public interface MybatisPlusPackage {

    String MODEL_PACKAGE = "com.baomidou.mybatisplus.activerecord.Model";

    String BASE_MAPPER_PACKAGE = "com.baomidou.mybatisplus.mapper.BaseMapper";

    String BASE_SERVICE = "com.github.kanon.datasource.mybatis.service.IMyBatisPlusService";

    String BASE_SERVICE_IMPL = "com.github.kanon.datasource.mybatis.service.AbstractMyBatisPlusService";

}
