package com.example.demo.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.example.demo.common.Result;
import com.example.demo.utils.Image2Base64Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PictureVerificationCodeController {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @GetMapping("/captcha/get")
    public Result generateCode(String curTime){
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(200, 100);
        String code = circleCaptcha.getCode();
        log.info("code:{}",code);
        BufferedImage image = circleCaptcha.getImage();
        redisTemplate.opsForValue().set(code,curTime,60, TimeUnit.SECONDS);
        String base64 = "";
        try {
            base64 = Image2Base64Util.bufferedImageToBase64(image);
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
        return Result.buildSucess(base64);

    }

}
