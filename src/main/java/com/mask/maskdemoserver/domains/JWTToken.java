package com.mask.maskdemoserver.domains;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Author: marico.lv
 * @CreateTime: 2020-03-24 10:25
 * @Description:
 */
public class JWTToken implements AuthenticationToken {
    private static final long serialVersionUID = 1L;

    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return this.token;
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }

    @Override
    public String toString() {
        return "JWTToken{" +
                "token='" + token + '\'' +
                '}';
    }
}
