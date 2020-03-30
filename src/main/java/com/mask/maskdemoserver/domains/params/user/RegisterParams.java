package com.mask.maskdemoserver.domains.params.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: marico.lv
 * @CreateTime: 2020-03-13 15:49
 * @Description:
 */
@Data
public class RegisterParams implements Serializable {
    private static final long serialVersionUID = -6709609143162764876L;

    private String loginname;
    private String password;
    private String phone;
    private String code;
    private String uuid;
}
