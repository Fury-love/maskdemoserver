package com.mask.maskdemoserver.domains.params.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: marico.lv
 * @CreateTime: 2020-03-25 10:47
 * @Description:
 */
@Data
public class CommonStringParams implements Serializable {
    private static final long serialVersionUID = -3960777168448206565L;

    String stringParams;
}
