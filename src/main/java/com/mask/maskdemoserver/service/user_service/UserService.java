package com.mask.maskdemoserver.service.user_service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mask.maskdemoserver.common.ResponseMessage;
import com.mask.maskdemoserver.domains.params.user.LoginUserParams;
import com.mask.maskdemoserver.domains.params.user.RegisterParams;
import com.mask.maskdemoserver.domains.po.User;

/**
 * @Author: marico.lv
 * @CreateTime: 2020-03-13 15:59
 * @Description:
 */
public interface UserService extends IService<User> {
    ResponseMessage register(RegisterParams registerParams);

    ResponseMessage login(LoginUserParams params);

    ResponseMessage checkLogin(String token);

    User findByUserName(String userName);

    ResponseMessage updateUserInfo(User user);
}
