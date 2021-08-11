package com.example.demo.controller;


import cn.hutool.core.util.RandomUtil;
import com.example.demo.common.Constant;
import com.example.demo.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class SendPhoneCodeController {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;



    @GetMapping("/sms/phone" + "/{phone}")
    public Result sendCode(HttpServletRequest request, @PathVariable String phone) {
        String code = RandomUtil.randomNumbers(6);
        log.info("向手机：" + phone + ",发送验证码：[" + code + "]");
        redisTemplate.opsForValue().set(Constant.SMS_CODE_KEY+phone,code,Constant.SMS_CODE_TIME, TimeUnit.SECONDS);
        return Result.buildSucess("send code success");
    }

}
