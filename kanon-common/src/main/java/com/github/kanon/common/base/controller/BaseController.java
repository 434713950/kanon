package com.github.kanon.common.base.controller;

import com.github.kanon.common.base.model.vo.ResponseParam;
import com.github.kanon.common.constants.MessageConstants;
import com.github.kanon.common.utils.spring.I18nUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: PengCheng
 * @Description:    controller 基础类。提供了基本操作的消息提示
 * @Date: 2018/5/20
 */
public abstract class BaseController {

    @Autowired
    private I18nUtil internationalizationUtil;

    protected ResponseParam getSuccessOperationResult() {
        return ResponseParam.success(null, internationalizationUtil.i18nHandler(MessageConstants.OPTION_SUCCESS_MSG));
    }

    protected ResponseParam getSuccessAddResult() {
        return ResponseParam.success(null, internationalizationUtil.i18nHandler(MessageConstants.OPTION_ADD_SUCCESS_MSG));
    }

    protected ResponseParam getSuccessUpdateResult() {
        return ResponseParam.success(null, internationalizationUtil.i18nHandler(MessageConstants.OPTION_UPDATE_SUCCESS_MSG));
    }

    protected ResponseParam getSuccessDeleteResult() {
        return ResponseParam.success(null, internationalizationUtil.i18nHandler(MessageConstants.OPTION_DELETE_SUCCESS_MSG));
    }

    protected ResponseParam getSuccessImportResult() {
        return ResponseParam.success(null, internationalizationUtil.i18nHandler(MessageConstants.OPTION_UPLOAD_SUCCESS_MSG));
    }

    protected ResponseParam getSuccessExportResult() {
        return ResponseParam.success(null, internationalizationUtil.i18nHandler(MessageConstants.OPTION_EXPORT_SUCCESS_MSG));
    }

}
