package com.github.kanon.generate.plugin.context;

import com.github.kanon.generate.plugin.config.ControllerConfiguration;
import com.github.kanon.generate.plugin.config.ServiceConfiguration;
import com.github.kanon.generate.plugin.generator.ControllerGeneratorAdapter;
import com.github.kanon.generate.plugin.generator.EntityGeneratorAdapter;
import com.github.kanon.generate.plugin.generator.MapperGeneratorAdapter;
import com.github.kanon.generate.plugin.generator.ServiceGeneratorAdapter;
import com.github.kanon.generate.plugin.generator.mybatisplus.MybatisPlusControllerGenerator;
import com.github.kanon.generate.plugin.generator.mybatisplus.MybatisPlusEntityGenerator;
import com.github.kanon.generate.plugin.generator.mybatisplus.MybatisPlusMapperGenerator;
import com.github.kanon.generate.plugin.generator.mybatisplus.MybatisPlusServiceGenerator;
import com.github.tool.io.IOUtil;
import lombok.Getter;
import lombok.Setter;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.internal.db.ConnectionFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/26
 */
public class BusinessGenerateContext {

    protected EntityGeneratorAdapter entityGenerator;

    protected MapperGeneratorAdapter mapperGenerator;

    protected ServiceGeneratorAdapter serviceGenerator;

    protected ControllerGeneratorAdapter controllerGenerator;

    protected String tableComment;

    /**
     * 实体对象
     */
    @Getter
    @Setter
    private TopLevelClass entity;

    /**
     * mapper对象
     */
    @Getter
    @Setter
    private Interface mapper;

    /**
     * service接口
     */
    @Getter
    @Setter
    private Interface iService;

    /**
     * service实现
     */
    @Getter
    @Setter
    private TopLevelClass service;

    /**
     * controller
     */
    @Getter
    @Setter
    private TopLevelClass controller;


    public String setTableComment(IntrospectedTable introspectedTable){
        tableComment = getTableComment(introspectedTable.getFullyQualifiedTable(),introspectedTable.getContext());
        return tableComment;
    }


    public boolean parseConfig(Properties properties){
        entityGenerator = new MybatisPlusEntityGenerator();
        mapperGenerator = new MybatisPlusMapperGenerator();

        ServiceConfiguration serviceConfiguration = new ServiceConfiguration();
        serviceConfiguration.parse(properties);
        if (serviceConfiguration.isNeed()) {
            serviceGenerator = new MybatisPlusServiceGenerator(serviceConfiguration);

            ControllerConfiguration controllerConfiguration = new ControllerConfiguration();
            controllerConfiguration.parse(properties);
            if (controllerConfiguration.isNeed()){
                controllerGenerator = new MybatisPlusControllerGenerator(controllerConfiguration);
            }

        }
        return true;
    }


    /**
     * 从数据库中获取表的注释
     * @param table
     * @return
     */
    private String getTableComment(FullyQualifiedTable table, Context context) {
        String tableComment = "";
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        JDBCConnectionConfiguration jdbcConnectionConfiguration = context.getJdbcConnectionConfiguration();
        try {
            connection = ConnectionFactory.getInstance().getConnection(
                    jdbcConnectionConfiguration);
            statement = connection.createStatement();
            rs = statement.executeQuery("SHOW CREATE TABLE " + table.getIntrospectedTableName());
            if (rs != null && rs.next()) {
                String createDDL = rs.getString(2);

                Matcher matcher = Pattern.compile("COMMENT=\'.*?\'").matcher(createDDL);
                if (matcher.find()){
                    tableComment = matcher.group().split("\'")[1];
                }
            }
        } catch (SQLException e) {
            //ignore
        } finally {
            IOUtil.close(rs);
            IOUtil.close(statement);
            IOUtil.close(connection);
        }
        return tableComment;
    }


    public EntityGeneratorAdapter getEntityGenerator() {
        return entityGenerator;
    }

    public MapperGeneratorAdapter getMapperGenerator() {
        return mapperGenerator;
    }

    public ServiceGeneratorAdapter getServiceGenerator() {
        return serviceGenerator;
    }

    public ControllerGeneratorAdapter getControllerGenerator() {
        return controllerGenerator;
    }

    public String getTableComment() {
        return tableComment;
    }
}
