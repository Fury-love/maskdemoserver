package com.mask.maskdemoserver.common;

/**
 * @Author: marico.lv
 * @CreateTime: 2019-08-29 09:45
 * @Description: 返回状态常量
 */
public enum ResponseStatus {
    OK("200","success"),
    ERROR("-1","未知异常"),
    FAILD_401("401","对不起,您无权限进行操作!"),
    FAILD_1001("1001","验证码不匹配"),
    FAILD_1002("1002","账号或密码不匹配"),
    FAILD_1003("1003","当前用户不可用，请联系管理员"),
    FAILD_1004("1004","该用户已注册"),
    FAILD_1005("1005","注册失败"),
    FAILD_1006("1006","登录失败"),
    FAILD_1007("1007","此账号不存在"),
    FAILD_1008("1008","验证过期，请重新登录"),
    FAILD_1009("1009","名称已被使用，请重新输入");


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
