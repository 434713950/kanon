package com.github.kanon.datasource.mybatis.handler;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * <p>mybatis-plus数据填充策略</p>
 *
 * @author pengcheng
 * @date 2018-11-06
 */
public class MyBatisPlusMetaObjectHandler extends MetaObjectHandler {


    /**
     * 新增时间自动填充
     * 如需使用，在对应属性上标注 @TableField (fill = FieldFill.INSERT)
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        setFieldValByName("createTime", new Date(), metaObject);
    }

    /**
     * 修改时间自动填充
     * 如需使用，在对应属性上标注 @TableField (fill = FieldFill.UPDATE)
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("updateTime", new Date(), metaObject);
    }
}
