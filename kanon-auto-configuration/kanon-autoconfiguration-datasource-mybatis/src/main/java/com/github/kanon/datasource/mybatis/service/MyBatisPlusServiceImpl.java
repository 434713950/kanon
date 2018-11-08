package com.github.kanon.datasource.mybatis.service;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.kanon.common.utils.spring.I18nUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>mybatis-plus使用的service模板</p>
 *
 * @author PengCheng
 * @date 15:33 2018/4/10/010
 */
public abstract class MyBatisPlusServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M,T> implements MyBatisPlusService<T> {

    @Autowired
    protected I18nUtil i18nUtil;

}
