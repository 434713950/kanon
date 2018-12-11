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
    private String menuCode;

    @ApiModelProperty(value = "父级菜单code")
    private String menuPCode;

    @ApiModelProperty(value = "菜单名")
    @NotEmpty(message = SysMenuValidation.SYS_MENU_NAME_EMPTY)
    private String menuName;

    @ApiModelProperty(value = "菜单名（国际化）")
    private String menuNameI18n;

    @ApiModelProperty(value = "菜单图标")
    private String menuIcon;

    @ApiModelProperty(value = "菜单对应的操作url")
    private String menuUrl;

    @ApiModelProperty(value = "菜单排序")
    private Integer num=0;
}
