package com.mask.maskdemoserver.service.user_service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.mask.maskdemoserver.common.ResponseMessage;
import com.mask.maskdemoserver.domains.params.user.LoginUserParams;
import com.mask.maskdemoserver.domains.params.user.RegisterParams;
import com.mask.maskdemoserver.domains.po.User;
import com.mask.maskdemoserver.exception.MyException;
import com.mask.maskdemoserver.service.login_service.CaptchaService;
import com.mask.maskdemoserver.service.token.TokenService;
import com.mask.maskdemoserver.service.user_service.UserMapper;
import com.mask.maskdemoserver.service.user_service.UserService;
import com.mask.maskdemoserver.utils.CommonUtils;
import com.mask.maskdemoserver.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import static com.mask.maskdemoserver.common.ResponseStatus.*;
import static com.mask.maskdemoserver.utils.CommonUtils.encryptToMD5;

/**
 * @Author: marico.lv
 * @CreateTime: 2020-03-13 16:00
 * @Description:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final CaptchaService captchaService;
    private final TokenService tokenService;
    @Autowired
    private UserMapper userMapper;

    public UserServiceImpl(CaptchaService captchaService, TokenService tokenService) {
        this.captchaService = captchaService;
        this.tokenService = tokenService;
    }

    @Override
    public ResponseMessage register(RegisterParams registerParams) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            if (!captchaService.verifyCaptcha(registerParams.getUuid(), registerParams.getCode())) {
                return responseMessage.error(FAILD_1001);
            }
            if (this.count(new QueryWrapper<User>().eq("login_name", registerParams.getLoginname())) > 0) {
                return responseMessage.error(FAILD_1004);
            } else {
                User userEntity = new User();
                userEntity.setLoginName(registerParams.getLoginname());
                userEntity.setNickname("用户" + "__" + CommonUtils.randomString(8));
                userEntity.setPassword(encryptToMD5(registerParams.getPassword()));
                userEntity.setPhone(registerParams.getPhone());
                this.save(userEntity);
                return responseMessage.ok(userEntity);
            }
        } catch (MyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return responseMessage.error(FAILD_1005);
        }
    }

    @Override
    public ResponseMessage login(LoginUserParams params) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            boolean captchaVerify = captchaService.verifyCaptcha(params.getUuid(), params.getVerifycode());
            if (!captchaVerify) {
                return responseMessage.error(FAILD_1001);
            }
            User user = findByUserName(params.getLoginName());
            ;
            if (user == null) {
                return responseMessage.error(FAILD_1007);
            }
            if (user.getStatus() == 1) {
                return responseMessage.error(FAILD_1003);
            }
            if (!user.getPassword().equals(encryptToMD5(params.getPassword()))) {
                return responseMessage.error(FAILD_1002);
            }
            String token = tokenService.refreshToken(user);
            Map<String, Object> resultMap = Maps.newHashMapWithExpectedSize(2);
            resultMap.put("token", token);
            resultMap.put("userInfo", user);
            responseMessage.ok(resultMap);
        } catch (MyException e) {
            e.printStackTrace();
            return responseMessage.error(FAILD_1006);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage checkLogin(String token) {
        ResponseMessage responseMessage = new ResponseMessage();
        String username = JWTUtils.getUserName(token);
        try {
            User user = findByUserName(username);
            responseMessage.ok(user);
        } catch (MyException e) {
            responseMessage.error(FAILD_1008);
        }
        return responseMessage;
    }

    @Override
    public User findByUserName(String userName) {
        return this.getOne(new QueryWrapper<User>().eq("login_name", userName));
    }

    @Override
    public ResponseMessage updateUserInfo(User user) {
        ResponseMessage responseMessage = new ResponseMessage();
        try{
            if(this.count(new QueryWrapper<User>().eq("nickname", user.getNickname())) > 0){
                return responseMessage.error(FAILD_1009);
            }
            this.updateById(user);
            responseMessage.setData(findByUserName(user.getLoginName()));
        }catch (MyException e){
            ResponseMessage.error();
        }
        return responseMessage;
    }
}
