package com.mask.maskdemoserver.common;

/**
 * @Author: marico.lv
 * @CreateTime: 2020-03-06 16:30
 * @Description:
 */
public interface UriMapperConstant {
    // 模块
    public static final String USER = "/user";
    public static final String CAPTCHA = "/captcha";

    // 服务
    public static final String INSERT = "/insert";
    public static final String UPDATE = "/update";
    public static final String REMOVE = "/remove";
    public static final String QUERY = "/query";

    public static final String AUTHCODE_JPG = "authcode.jpg";
    public static final String USER_LOGIN = "/login";
    public static final String USER_CHECK_LOGIN = "/checklogin";
    public static final String USER_REGISTER = "/register";
    public static final String USER_SENDSMS = "/send";
}
