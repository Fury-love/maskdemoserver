package com.mask.maskdemoserver.common;

/**
 * @Author: marico.lv
 * @CreateTime: 2019-08-29 09:45
 * @Description: 返回状态常量
 */
public enum ResponseStatus {
    OK("200","success"),
    ERROR("-1","未知异常"),
    FAILD_1001("1001","验证码不匹配"),
    FAILD_1002("1002","账号或密码不匹配"),
    FAILD_1003("1003","当前用户不可用，请联系管理员");


    private final String code;
    private final String message;
    ResponseStatus(String code,String message){
        this.code = code;
        this.message = message;
    }
    public String getCode(){
        return code;
    }
    public String getMessage(){
        return message;
    }

}
