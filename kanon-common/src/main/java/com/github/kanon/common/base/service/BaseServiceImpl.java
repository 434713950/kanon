package com.github.kanon.common.base.service;

import com.github.kanon.common.utils.spring.I18nUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 15:33 2018/4/10/010
 */
@Slf4j
public abstract class BaseServiceImpl implements BaseService {

    /**
     * 国际化处理工具
     */
    @Autowired
    private I18nUtil internationalizationUtil;
}
