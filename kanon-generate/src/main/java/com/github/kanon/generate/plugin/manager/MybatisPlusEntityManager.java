package com.github.kanon.generate.plugin.manager;

import com.github.kanon.generate.anno.CommonlyAnnoEnum;
import com.github.kanon.generate.comment.JavaCommentTag;
import com.github.kanon.generate.plugin.MybatisPlusPackage;
import com.github.kanon.generate.util.AnnoAdjunctionUtil;
import com.github.kanon.generate.util.IOUtil;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.internal.db.ConnectionFactory;
import org.mybatis.generator.internal.util.StringUtility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/25
 */
public class MybatisPlusEntityManager {

    public static final Pattern COMMENT_TABLE_PATTERN = Pattern.compile("COMMENT=\'.*?\'");


    public static void generateEntity(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, Context context){
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
        //表注释
        String tableComment = getTableComment(table,context);

        //加注释
        addClassComment(topLevelClass,table,tableComment);
        //加注解
        addClassAnno(topLevelClass,table,tableComment);

        addSuper(topLevelClass);
    }

    /**
     * 添加父类
     * @param topLevelClass
     */
    private static void addSuper(TopLevelClass topLevelClass){
        topLevelClass.addImportedType(MybatisPlusPackage.MODEL_PACKAGE);
        FullyQualifiedJavaType modelType = new FullyQualifiedJavaType(MybatisPlusPackage.MODEL_PACKAGE);
        modelType.addTypeArgument(topLevelClass.getType());
        topLevelClass.setSuperClass(modelType);

        //添加重写方法
        Method method = new Method("pkVal");
        method.setVisibility(JavaVisibility.PROTECTED);
        //默认返回null
        method.addBodyLine("return null;");

        //返回参数
        topLevelClass.addImportedType("java.io.Serializable");
        method.setReturnType(new FullyQualifiedJavaType("Serializable"));
        //重写注解
        method.addAnnotation("@Override");
        topLevelClass.addMethod(method);
    }

    /**
     * 添加pojo类表注释
     * @param topLevelClass
     * @param table
     */
    private static void addClassComment(TopLevelClass topLevelClass, FullyQualifiedTable table, String tableComment) {
        topLevelClass.addJavaDocLine("/**");
        if(StringUtility.stringHasValue(tableComment)) {
            topLevelClass.addJavaDocLine(" * <p>" + tableComment + "<p/>");
        }
        topLevelClass.addJavaDocLine(" * <p>" + table.toString() + "<p/>");
        JavaCommentTag.addJavaAuthorTag(topLevelClass);
        JavaCommentTag.addJavaDateTag(topLevelClass);
        topLevelClass.addJavaDocLine(" */");
    }

    /**
     * 添加通用的注解
     * @param topLevelClass
     * @param table
     */
    private static void addClassAnno(TopLevelClass topLevelClass, FullyQualifiedTable table,String tableComment){
        AnnoAdjunctionUtil.addClassAnno(topLevelClass, CommonlyAnnoEnum.LOMBOK_DATA);

        Map<String,String> annoContentMap = new HashMap<>();
        annoContentMap.put("chain","true");
        AnnoAdjunctionUtil.addClassAnno(topLevelClass, CommonlyAnnoEnum.LOMBOK_ACCESSOR,annoContentMap);

        annoContentMap.clear();
        annoContentMap.put("value","\""+table.toString()+"\"");
        AnnoAdjunctionUtil.addClassAnno(topLevelClass, CommonlyAnnoEnum.MYBATIS_TABLE,annoContentMap);

        annoContentMap.clear();
        annoContentMap.put("value","\""+topLevelClass.getType().getShortName()+"\"");
        annoContentMap.put("description","\""+tableComment+"\"");
        AnnoAdjunctionUtil.addClassAnno(topLevelClass, CommonlyAnnoEnum.SWAGGER_API_MODEL,annoContentMap);
    }



    /**
     * 从数据库中获取表的注释
     * @param table
     * @return
     */
    private static String getTableComment(FullyQualifiedTable table, Context context) {
        String tableComment = "";
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = getConnection(context.getJdbcConnectionConfiguration());
            statement = connection.createStatement();
            rs = statement.executeQuery("SHOW CREATE TABLE " + table.getIntrospectedTableName());
            if (rs != null && rs.next()) {
                String createDDL = rs.getString(2);

                Matcher matcher = COMMENT_TABLE_PATTERN.matcher(createDDL);
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

    private static Connection getConnection(JDBCConnectionConfiguration jdbcConnectionConfiguration) throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection(
                jdbcConnectionConfiguration);
        return connection;
    }
}
