package com.project.data_cloud_server.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.project.data_cloud_server.common.api.ApiResult;
import com.project.data_cloud_server.common.constValue.Const;
import com.project.data_cloud_server.entity.SysUser;
import com.project.data_cloud_server.service.UserService;
import com.project.data_cloud_server.util.MailUtil;
import com.project.data_cloud_server.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
public class UserController {
    @Autowired
    DefaultKaptcha producer;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    MailUtil mailUtil;
    @Autowired
    UserService userService;
    @GetMapping("/captcha")
    public ApiResult captcha() throws IOException {
        Map<String,Object> map=new HashMap<>();
        //生成文本验证码
        String code= producer.createText();
        log.info("生成的验证码为：{}",code);
        //生成图片验证码
        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        ImageIO.write(image,"jpg",outputStream);
        String key= UUID.randomUUID().toString();
        String str = "data:image/jpeg;base64,";
        String base64Img=str+ Base64.getEncoder().encodeToString(outputStream.toByteArray());
        //随机key和文本验证码存进redis
        redisUtil.hset(Const.CAPTCHA_KEY,key,code,Const.RedisTime);
        map.put("key",key);
        map.put("captchaImg",base64Img);
        return ApiResult.success(map);

    }
    @PostMapping("/emailCode")
    @Async
    public ApiResult emailCode(String email){
        String code= mailUtil.generateCode();
        String text=Const.MAIL_TEXT+code;
        mailUtil.sendMail(Const.MAIL_FROM,email,Const.MAIL_SUBJECT,text);
        log.info("验证码已发送");
        redisUtil.hset(Const.EMAIL_CODE,"code",code,Const.RedisTime);
        log.info("注册验证码已存入redis");
        return ApiResult.success();
    }
    @PostMapping("/register")
    public ApiResult register(SysUser user,String code,String password2){
        return userService.register(user,code);
    }
}
