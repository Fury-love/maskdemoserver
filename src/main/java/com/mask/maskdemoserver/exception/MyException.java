package com.mask.maskdemoserver.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Marico.lv
 * Date: 2019/3/25
 * Time: 15:25
 */
public class MyException extends RuntimeException {
    public MyException(){}
    public MyException(String message){
        super(message);
    }
    public MyException(Throwable cause) {
        super(cause);
    }

    public MyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
