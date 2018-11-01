package com.github.kanon.common.constants;

/**
 * @Author: PengCheng
 * @Description:    常用正则常量
 * @Date: 2018/5/9
 */
public interface RegexConstants {

    /**
     * 密码(以字母开头，长度在8~16之间，只能包含字母、数字)
     */
    String PASSWORD = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";

    /**
     * 强密码(必须包含大小写字母和数字的组合，不能使用特殊字符，长度在8-10之间)
     */
    String STORAGE_PASSWORD = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,10}$";

    /**
     * e-mail
     */
    String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    /**
     * 身份证
     */
    String ID_CARD = "^\\d{15}|\\d{18}$";

    /**
     * 手机号码
     */
    String MOBILE = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";

    /**
     *  电话号码("XXX-XXXXXXX"、"XXXX-XXXXXXXX"、"XXX-XXXXXXX"、"XXX-XXXXXXXX"、"XXXXXXX"和"XXXXXXXX)
     */
    String PHONE = "^(\\(\\d{3,4}-)|\\d{3.4}-)?\\d{7,8}$";

    /**
     * 日期格式
     */
    String DATE = "^\\d{4}-\\d{1,2}-\\d{1,2}";

    /**
     * ip地址
     */
    String IP = "\\d+\\.\\d+\\.\\d+\\.\\d+";
}
