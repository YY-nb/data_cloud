package com.project.data_cloud_server.config.security;

import com.project.data_cloud_server.common.constValue.Const;
import com.project.data_cloud_server.common.exception.CaptchaException;
import com.project.data_cloud_server.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class MyAuthenticationProvider extends DaoAuthenticationProvider {
    @Autowired
    RedisUtil redisUtil;
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        HttpServletRequest request= ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        validate(request);
        super.additionalAuthenticationChecks(userDetails, authentication);
    }
    /**
     * 校验验证码
     * @param request
     */
    private void validate(HttpServletRequest request) {
        String code=request.getParameter("code");
        String key=request.getParameter("key");
        if(null==code||null==key){
            throw new CaptchaException("验证码不能为空");
        }
        if(!code.equals(redisUtil.hget(Const.CAPTCHA_KEY,key))){
            throw new CaptchaException("验证码错误");
        }
        //使用一次后删除
        redisUtil.hdel(Const.CAPTCHA_KEY,key);
    }
}
