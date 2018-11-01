package com.github.kanon.common.handle;

import com.github.kanon.common.base.vo.ResponseParam;
import com.github.kanon.common.exceptions.AuthorityException;
import com.github.kanon.common.exceptions.ErrorMsgException;
import com.github.kanon.common.exceptions.InitialPasswordException;
import com.github.kanon.common.exceptions.LoginNotException;
import com.github.kanon.common.utils.spring.I18nUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Author: PengCheng
 * @Description:    自定义统一异常处理器
 * @Date: 2018/5/20
 */
@ControllerAdvice
@ResponseBody
public class CustomExceptionHandler {

    @Autowired
    private I18nUtil i18nUtil;

    @ExceptionHandler(ErrorMsgException.class)
    public ResponseParam handleErrorMsgException(ErrorMsgException e){
        String msg = i18nUtil.i18nHandler(e.getMsg(),e.getText());
        return new ResponseParam(ErrorMsgException.CODE,msg);
    }

    @ExceptionHandler(LoginNotException.class)
    public ResponseParam handleLoginNotException(LoginNotException e){
        String msg = i18nUtil.i18nHandler(LoginNotException.MSG);
        return new ResponseParam(LoginNotException.CODE,msg);
    }

    @ExceptionHandler(InitialPasswordException.class)
    public ResponseParam handleInitialPasswordException(InitialPasswordException e){
        String msg = i18nUtil.i18nHandler(InitialPasswordException.MSG);
        return new ResponseParam(InitialPasswordException.CODE,msg);
    }

    @ExceptionHandler(AuthorityException.class)
    public ResponseParam handleAuthorityException(AuthorityException e){
        String msg = i18nUtil.i18nHandler(AuthorityException.MSG);
        return new ResponseParam(AuthorityException.CODE,msg);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseParam handleException(Exception e){
        return ResponseParam.systemError();
    }

}
