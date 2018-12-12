package com.github.kanon.upms.model.dto;

import com.github.kanon.common.base.model.dto.BaseDto;
import com.github.kanon.upms.vaildation.SysMenuValidation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/6
 */
@Data
public class SysMenuDto extends BaseDto {

    @ApiModelProperty(value = "菜单id")
    private Long id;

    @ApiModelProperty(value = "菜单code")
    @NotEmpty(message = SysMenuValidation.SYS_MENU_CODE_EMPTY)
    private String code;

    @ApiModelProperty(value = "父级菜单code")
    private String pCode;

    @ApiModelProperty(value = "菜单名")
    @NotEmpty(message = SysMenuValidation.SYS_MENU_NAME_EMPTY)
    private String name;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "菜单对应的操作url")
    private String url;

    @ApiModelProperty(value = "菜单排序")
    private Integer num=0;
}
