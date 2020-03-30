package com.mask.maskdemoserver.controller;

import com.mask.maskdemoserver.common.AbstractController;
import com.mask.maskdemoserver.service.login_service.CaptchaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.mask.maskdemoserver.common.UriMapperConstant.AUTHCODE_JPG;
import static com.mask.maskdemoserver.common.UriMapperConstant.CAPTCHA;

/**
 * @Author: marico.lv
 * @CreateTime: 2020-03-20 10:29
 * @Description:
 */
@Api(tags = "验证码")
@RestController
@RequestMapping(CAPTCHA)
public class CaptchaController extends AbstractController {

    @Autowired
    private CaptchaService captchaService;

    @ApiOperation(value = "获取验证码", notes = "")
    @GetMapping(AUTHCODE_JPG)
    public void captcha(HttpServletResponse response, String uuid) throws IOException {
        response.setHeader("Contral-Cache", "no-store,no-cache");
        response.setContentType("image/jepg");
        BufferedImage image = captchaService.getCaptcha(uuid);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }
}
