package com.mask.maskdemoserver.service.token;

import com.mask.maskdemoserver.domains.po.User;
import com.mask.maskdemoserver.utils.JWTUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @Author: marico.lv
 * @CreateTime: 2020-03-23 14:14
 * @Description:
 */
@Component
public class TokenService {

    /**
     * 刷新 / 新建token
     * @param user
     * @return
     */
    public String refreshToken(User user){
        String sessionId = UUID.randomUUID().toString();
        return JWTUtils.sign(user,sessionId);
    }


}
