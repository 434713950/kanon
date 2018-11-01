package com.github.kanon.common.utils.spring;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * @Author: PengCheng
 * @Description:    国际化工具
 * @Date: 2018/5/20
 */
@Component
public class I18nUtil {

    @Autowired
    private MessageSource messageSource;

    public String i18nHandler(String i18nMsg,String ... text){
        String msg;
        try {
            if (messageSource == null){
                msg = i18nMsg;
            }else {
                msg = messageSource.getMessage(i18nMsg.substring(1,i18nMsg.length()-1), null, LocaleContextHolder.getLocale());
            }
        }catch (NoSuchMessageException x){
            msg = i18nMsg;
        }
        if (text!=null && text.length>0 && !StringUtils.isEmpty(msg)){
            for (int i=0;i<text.length;i++){
                msg = msg.replace("{"+i+"}",text[i]);
            }
        }
        return msg;
    }
}
