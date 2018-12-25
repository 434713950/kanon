package com.github.kanon.generate.util;

import com.github.kanon.generate.comment.JavaCommentTag;
import org.mybatis.generator.api.dom.java.JavaElement;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/25
 */
public class CommentTagUtil {

    /**
     *  添加统一注释
     * @param javaElement
     */
    public static void addUnifyComment(JavaElement javaElement){
        javaElement.addJavaDocLine("/**");
        javaElement.addJavaDocLine(" * auto generate by kanon ");
        JavaCommentTag.addJavaAuthorTag(javaElement);
        JavaCommentTag.addJavaDateTag(javaElement);
        javaElement.addJavaDocLine(" */");
    }
}
