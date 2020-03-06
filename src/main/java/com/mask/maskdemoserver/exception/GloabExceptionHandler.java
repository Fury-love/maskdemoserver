package com.mask.maskdemoserver.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: Marico.lv
 * Date: 2019/3/25
 * Time: 15:50
 */
@ControllerAdvice //所有requestmapping执行前执行，可用于异常处理，初始化等操作
public class GloabExceptionHandler {

    public ErrorInfo<String> jsonErrorHandler(HttpServletRequest req, MyException e) throws Exception{
        ErrorInfo<String> r = new ErrorInfo<String>();
        r.setMessage(e.getMessage());
        r.setCode(ErrorInfo.ERROR);
        r.setData("some data");
        r.setUrl(req.getRequestURL().toString());
        return r;
    }
}
