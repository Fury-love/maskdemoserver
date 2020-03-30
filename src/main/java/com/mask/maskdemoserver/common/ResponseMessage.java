package com.mask.maskdemoserver.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import java.io.Serializable;

/**
 * @Author: marico.lv
 * @CreateTime: 2020-03-06 16:28
 * @Description: 返回实体
 */
@ApiModel(description = "响应结果")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResponseMessage implements Serializable {
    private static final long serialVersionUID = -8590551160540335055L;
    private String code;
    private String message;
    private Object data;
    private boolean success;
    private long timestamp;

    public ResponseMessage() {
        this.code = ResponseStatus.OK.getCode();
        this.message = ResponseStatus.OK.getMessage();
        this.data = null;
        this.success = true;
        this.timestamp = System.currentTimeMillis();
    }

    public static ResponseMessage ok() {
        return new ResponseMessage();
    }

    public ResponseMessage ok(String message) {
        this.message = message;
        return this;
    }

    public ResponseMessage ok(Object data) {
        this.data = data;
        return this;
    }

    public ResponseMessage error(ResponseStatus status) {
        this.message = status.getMessage();
        this.code = status.getCode();
        return this;
    }

    public static ResponseMessage error() {
        return new ResponseMessage().error(ResponseStatus.ERROR);
    }

    public ResponseMessage error(String message) {
        ResponseMessage responseMessage = ResponseMessage.error();
        responseMessage.setMessage(message);
        return responseMessage;
    }

}
