package com.mask.maskdemoserver.common;

import java.io.Serializable;

/**
 * @Author: marico.lv
 * @CreateTime: 2020-03-06 16:28
 * @Description: 返回实体
 */
public class ResponseMessage<T> implements Serializable {
    private static final long serialVersionUID = -8590551160540335055L;
    private String code = "200";
    private String message = "success";
    private T data;

    public ResponseMessage() {
    }

    public ResponseMessage<T> code(String code) {
        this.code = code;
        return this;
    }

    public ResponseMessage<T> message(String message) {
        this.message = message;
        return this;
    }

    public ResponseMessage<T> data(T data) {
        this.data = data;
        return this;
    }

    public static <T> ResponseMessage<T> OK() {
        return OK(null);
    }

    public static <T> ResponseMessage<T> OK(T data) {
        return new ResponseMessage<T>().code(ResponseStatus.OK.getCode()).message(ResponseStatus.OK.getMessage()).data(data);
    }

    public static <T> ResponseMessage<T> ERROR() {
        return ERROR(ResponseStatus.ERROR);
    }

    public static <T> ResponseMessage<T> ERROR(ResponseStatus status) {
        return ERROR(status,null);
    }

    public static <T> ResponseMessage<T> ERROR(ResponseStatus status, T data) {
        ResponseMessage<T> res = new ResponseMessage<>();
        res.code(status.getCode());
        res.message(status.getMessage());
        res.data(data);
        return res;
    }
}
