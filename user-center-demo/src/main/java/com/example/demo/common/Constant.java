package com.example.demo.common;

public class Constant {
    /**
     * 短信验证码失效时间
     */
    public static final int SMS_CODE_TIME = 180;

    /**
     * 发送短信验证码接口验证码参数
     */
    public static final String SMS_CODE_PARAM = "code";

    /**
     * 验证码存入redis数据格式smsCode:17712341234
     */
    public static final String SMS_CODE_KEY = "smsCode:";

    /**
     * oauth2授权模式，短信验证码模式
     */
    public static final String GRANT_TYPE_SMS = "sms_code";

    /**
     * auth2授权模式，用户名密码模式
     */
    public static final String GRANT_TYPE_PWD = "password";

    /**
     * uth2授权模式，刷新token模式
     */
    public static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";

    /**
     * 手机号,短信验证码获取token 手机号参数
     */
    public static final String PHONE = "phone";

    public static final String USER_NAME = "username";

    public static final String CAPTCHA = "captcha";

    public static final String CAPTCHA_CREATE_TIME = "captcha_create_time";


}
