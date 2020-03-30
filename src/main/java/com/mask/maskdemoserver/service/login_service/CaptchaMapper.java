package com.mask.maskdemoserver.service.login_service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mask.maskdemoserver.domains.po.Captcha;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created with IntelliJ IDEA.
 * User: Marico.lv
 * Date: 2019/5/16
 * Time: 16:14
 */
@Mapper
public interface CaptchaMapper extends BaseMapper<Captcha> {

}
