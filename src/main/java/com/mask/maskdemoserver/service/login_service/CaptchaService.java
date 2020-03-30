package com.mask.maskdemoserver.service.login_service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mask.maskdemoserver.domains.po.Captcha;

import java.awt.image.BufferedImage;

/**
 * 动态校验图接口.
 * User: Marico.lv
 * Date: 2019/5/15
 * Time: 15:16
 */
public interface CaptchaService extends IService<Captcha> {
    /**
     * 获取验证码
     */
    BufferedImage getCaptcha(String uuid);

    /**
     * 检验验证码
     */
    boolean verifyCaptcha(String uuid, String code);
}
