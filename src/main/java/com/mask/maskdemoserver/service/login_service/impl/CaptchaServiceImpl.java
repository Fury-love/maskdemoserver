package com.mask.maskdemoserver.service.login_service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.code.kaptcha.Producer;
import com.mask.maskdemoserver.domains.po.Captcha;
import com.mask.maskdemoserver.exception.MyException;
import com.mask.maskdemoserver.service.login_service.CaptchaMapper;
import com.mask.maskdemoserver.service.login_service.CaptchaService;
import com.mask.maskdemoserver.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.awt.image.BufferedImage;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Marico.lv
 * Date: 2019/5/15
 * Time: 16:46
 */
@Service
public class CaptchaServiceImpl extends ServiceImpl<CaptchaMapper, Captcha> implements CaptchaService {

    @Autowired
    public Producer producer;

    public BufferedImage getCaptcha(String uuid) {
        if (StringUtils.isEmpty(uuid)) {
            throw new MyException("uuid不能为空");
        }
        //生成校验码
        String code = producer.createText();
        Captcha captcha = new Captcha();
        captcha.setUuid(uuid);
        captcha.setCode(code);
        //设置有效时间 3 分钟
        captcha.setExpireDate(DateUtils.addMinute(new Date(), 3));
        this.save(captcha);
        return producer.createImage(code);
    }

    @Override
    public boolean verifyCaptcha(String uuid, String code) {
        Captcha captcha = this.getOne(new QueryWrapper<Captcha>().eq("UUID",uuid));
        if(captcha == null){
            return false;
        }

        if(captcha.getCode().equalsIgnoreCase(code)){
            return true;
        }
        return false;
    }
}
