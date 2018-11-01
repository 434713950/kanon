package com.github.kanon.monitor.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>手机相关配置</p>
 *
 * @author PengCheng
 * @date 2018/10/31
 */
@Data
@ConditionalOnExpression("!'${mobiles}'.isEmpty()")
public class MonitorMobileProperties {

    /**
     * 是否启用短信通知
     */
    private Boolean enabled;

    /**
     * 需要通知的手机号
     */
    private List<String> mobiles = new ArrayList<>();
}
