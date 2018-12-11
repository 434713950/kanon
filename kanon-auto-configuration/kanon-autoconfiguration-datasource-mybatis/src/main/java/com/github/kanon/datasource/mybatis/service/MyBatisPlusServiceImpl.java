package com.github.kanon.datasource.mybatis.service;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

/**
 * <p>mybatis-plus使用的service模板</p>
 *
 * @author PengCheng
 * @date 15:33 2018/4/10/010
 */
public abstract class MyBatisPlusServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M,T> implements MyBatisPlusService<T> {

}
