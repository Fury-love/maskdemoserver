package com.mask.maskdemoserver.controller;

import com.mask.maskdemoserver.common.AbstractController;
import com.mask.maskdemoserver.common.ResponseMessage;
import com.mask.maskdemoserver.domains.params.user.CommonStringParams;
import com.mask.maskdemoserver.domains.params.user.LoginUserParams;
import com.mask.maskdemoserver.domains.params.user.RegisterParams;
import com.mask.maskdemoserver.domains.po.User;
import com.mask.maskdemoserver.service.user_service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.mask.maskdemoserver.common.UriMapperConstant.*;

/**
 * @Author: marico.lv
 * @CreateTime: 2020-03-06 17:23
 * @Description:
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping(USER)
public class UserController extends AbstractController {

//    @Autowired
//    private LoginService loginService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "发送短信", notes = "")
    @PostMapping(USER_SENDSMS)
    public ResponseMessage sendSms() {
        // TODO
        return ResponseMessage.ok();
    }

    @ApiOperation(value = "用户登录", notes = "")
    @PostMapping(USER_LOGIN)
    public ResponseMessage login(@RequestBody LoginUserParams params) {
        return userService.login(params);
    }

    @ApiOperation(value = "token验证", notes = "")
    @PostMapping(USER_CHECK_LOGIN)
    public ResponseMessage checkToken(@RequestBody CommonStringParams params) {
        return userService.checkLogin(params.getStringParams());
    }

    @ApiOperation(value = "用户注册", notes = "")
    @PostMapping(USER_REGISTER)
    public ResponseMessage register(@RequestBody RegisterParams registerParams) {
        return userService.register(registerParams);
    }

    @ApiOperation(value = "更新用户信息", notes = "")
    @PostMapping(UPDATE)
    public ResponseMessage updateUserInfo(@RequestBody User user) {
        return userService.updateUserInfo(user);
    }
}
