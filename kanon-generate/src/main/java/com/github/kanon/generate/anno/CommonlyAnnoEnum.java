package com.github.kanon.generate.anno;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/24
 */
public enum CommonlyAnnoEnum {

    LOMBOK_DATA("@Data","lombok.Data"),
    LOMBOK_ACCESSOR("@Accessors","lombok.experimental.Accessors"),
    MYBATIS_TABLE("@TableName","com.baomidou.mybatisplus.annotations.TableName"),
    SWAGGER_API_MODEL("@ApiModel","io.swagger.annotations.ApiModel"),
    SWAGGER_API_MODEL_PROP("@ApiModelProperty","io.swagger.annotations.ApiModelProperty"),
    SPRING_SERVICE("@Service","org.springframework.stereotype.Service");

    private String annoName;

    private String annoPackage;

    CommonlyAnnoEnum(String annoName, String annoPackage) {
        this.annoName = annoName;
        this.annoPackage = annoPackage;
    }

    public String getAnnoName() {
        return annoName;
    }

    public String getAnnoPackage() {
        return annoPackage;
    }}
