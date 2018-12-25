package com.github.kanon.generate.comment;

import com.github.kanon.generate.util.DateUtil;
import org.mybatis.generator.api.dom.java.JavaElement;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/19
 */
public class JavaCommentTag {

    public static final String AUTHOR = "@author";

    public static final String DATE = "@date";

    public static final String PARAM = "@param";

    public static final String RETURN = "@return";

    public static void addJavaTag(JavaElement javaElement, String javaCommentTag, String tagValue){
        StringBuilder sb  = new StringBuilder();
        sb.append(" * ").append(javaCommentTag).append(" ").append(tagValue);
        javaElement.addJavaDocLine(sb.toString());
    }

    /**
     * 添加日期注释标签
     * @param javaElement
     */
    public static void addJavaDateTag(JavaElement javaElement){
        addJavaTag(javaElement, JavaCommentTag.DATE, DateUtil.dateTimeFormat(DateUtil.getCurrentSystemTime()));
    }

    public static void addJavaAuthorTag(JavaElement javaElement){
        addJavaTag(javaElement, JavaCommentTag.AUTHOR,System.getProperties().getProperty("user.name"));
    }
}
