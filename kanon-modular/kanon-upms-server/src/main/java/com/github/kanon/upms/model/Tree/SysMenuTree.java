package com.github.kanon.upms.model.Tree;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.github.kanon.common.base.model.vo.BaseVo;
import com.github.tool.common.DateUtil;
import com.github.tool.tree.model.TreeNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/5
 */
@Data
@Builder
public class SysMenuTree extends TreeNode<SysMenuTree> {

    @ApiModelProperty(value = "菜单id")
    private Long id;

    @ApiModelProperty(value = "菜单名")
    private String name;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "菜单的url")
    private String url;

    @ApiModelProperty(value = "菜单排序")
    private Integer num;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = DateUtil.DATE_TIME_FORMAT)
    @JSONField(format = DateUtil.DATE_TIME_FORMAT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @DateTimeFormat(pattern = DateUtil.DATE_TIME_FORMAT)
    @JSONField(format = DateUtil.DATE_TIME_FORMAT)
    private Date updateTime;

}
