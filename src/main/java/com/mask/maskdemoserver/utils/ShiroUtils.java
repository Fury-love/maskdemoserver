package com.mask.maskdemoserver.utils;

import com.mask.maskdemoserver.domains.params.user.LoginUserParams;
import com.mask.maskdemoserver.domains.po.User;
import com.mask.maskdemoserver.service.token.TokenService;
import com.mask.maskdemoserver.service.user_service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.security.NoSuchAlgorithmException;
import static com.mask.maskdemoserver.utils.CommonUtils.encryptToMD5;

/**
 * @Author: marico.lv
 * @CreateTime: 2020-03-23 13:16
 * @Description:
 */
@Component
public class ShiroUtils {
    private static UserService sysUserService;
    private static TokenService systokenService;

    @Autowired
    private UserService userService;
    private TokenService tokenService;

    /* servlet创建时运行 */
    @PostConstruct
    public void init() {
        sysUserService = userService;
        systokenService = tokenService;
    }

    /**
     * 获取当前登录用户Token
     *
     * @return Token
     */
    public static String getToken() {
        return SecurityUtils.getSubject().getPrincipal().toString();
    }

    public static User getUser() throws NoSuchAlgorithmException {
        User user = null;
        LoginUserParams loginUserParams = (LoginUserParams) SecurityUtils.getSubject().getPrincipal();
        if (loginUserParams != null) {
            user = sysUserService.findByUserName(loginUserParams.getLoginName());
        }
        if (user == null) {
            user = new User();
            user.setLoginName(loginUserParams.getLoginName());
            user.setPassword(encryptToMD5(loginUserParams.getPassword()));
        }
        return user;
    }
}
