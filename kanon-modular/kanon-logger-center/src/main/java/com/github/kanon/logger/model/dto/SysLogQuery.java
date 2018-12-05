package com.github.kanon.logger.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.kanon.common.base.model.dto.PageQuery;
import com.github.tool.common.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/6/28
 */
@Data
@ApiModel(value = "LoggerQuery",description = "日志分页查询实体")
public class SysLogQuery extends PageQuery {

    @DateTimeFormat(pattern = DateUtil.DATE_TIME_FORMAT)
    @JSONField(format = DateUtil.DATE_TIME_FORMAT)
    @ApiModelProperty("建立开始时间")
    private Date createStartTime;

    @DateTimeFormat(pattern = DateUtil.DATE_TIME_FORMAT)
    @JSONField(format = DateUtil.DATE_TIME_FORMAT)
    @ApiModelProperty("建立结束时间")
    private Date createEndTime;
}
