package com.github.kanon.generate.util;

import com.github.kanon.generate.anno.CommonlyAnnoEnum;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>添加注解工具类</p>
 *
 * @author PengCheng
 * @date 2018/12/24
 */
public class AnnoAdjunctionUtil {

    public static void addClassAnno(TopLevelClass topLevelClass, CommonlyAnnoEnum commonlyAnnoEnum){
        addClassAnno(topLevelClass, commonlyAnnoEnum,null);
    }

    public static void addClassAnno(TopLevelClass topLevelClass, CommonlyAnnoEnum commonlyAnnoEnum, Map<String,String> annoContentMap){
        addAnno(null,topLevelClass, commonlyAnnoEnum,annoContentMap, AnnoType.CLASS);
    }

    public static void addFieldAnno(Field field, TopLevelClass topLevelClass, CommonlyAnnoEnum commonlyAnnoEnum){
        addFieldAnno(field,topLevelClass, commonlyAnnoEnum,null);
    }

    public static void addFieldAnno(Field field, TopLevelClass topLevelClass, CommonlyAnnoEnum commonlyAnnoEnum, Map<String,String> annoContentMap){
        addAnno(field,topLevelClass, commonlyAnnoEnum,annoContentMap, AnnoType.FIELD);
    }

    public static void addAnno(Field field, TopLevelClass topLevelClass, CommonlyAnnoEnum commonlyAnnoEnum, Map<String,String> annoContentMap, AnnoType annoType){
        topLevelClass.addImportedType(commonlyAnnoEnum.getAnnoPackage());
        StringBuilder annoName = new StringBuilder(commonlyAnnoEnum.getAnnoName());
        if (annoContentMap!=null && !annoContentMap.isEmpty()){
            annoName.append("(");
            for (Map.Entry<String,String> entry: annoContentMap.entrySet()){
                annoName.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
            }
            annoName = new StringBuilder(annoName.substring(0,annoName.length()-1));
            annoName.append(")");
        }

        switch (annoType){
            case CLASS:
                topLevelClass.addAnnotation(annoName.toString());
                break;
            case FIELD:
                field.addAnnotation(annoName.toString());
                break;
        }
    }

    public enum AnnoType{
        METHOD,FIELD,CLASS;
    }
}
