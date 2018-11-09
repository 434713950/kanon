package com.github.kanon.common.base.model.entity;

import lombok.Data;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;

import java.io.Serializable;
import java.util.Set;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/11/9
 */
@Data
public class ZuulRoute extends ZuulProperties.ZuulRoute implements Serializable {
}
