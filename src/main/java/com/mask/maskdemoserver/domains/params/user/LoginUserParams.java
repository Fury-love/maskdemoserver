package com.mask.maskdemoserver.domains.params.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: marico.lv
 * @CreateTime: 2020-03-23 09:37
 * @Description:
 */
@Data
public class LoginUserParams implements Serializable {
    private static final long serialVersionUID = 3221278001784121856L;

    private String loginName;
    private String password;
    private String uuid;
    private String verifycode;
}
