package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 短信验证码
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsCode implements Serializable {


    protected String code;

    protected int expireTime;

}
