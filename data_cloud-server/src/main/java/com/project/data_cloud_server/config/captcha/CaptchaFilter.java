package com.project.data_cloud_server.config.captcha;

import com.project.data_cloud_server.config.security.LoginFailureHandler;
import com.project.data_cloud_server.common.constValue.Const;
import com.project.data_cloud_server.common.exception.CaptchaException;
import com.project.data_cloud_server.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CaptchaFilter extends OncePerRequestFilter {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    LoginFailureHandler loginFailureHandler;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String url=httpServletRequest.getRequestURI();
        //只有登录请求才要经过此处理器
        if(url.equals(Const.LOGIN_URL)&&httpServletRequest.getMethod().equals("POST")){
            try{
                validate(httpServletRequest);
            }catch (CaptchaException e){
                //出现异常则交给登录失败处理器
                log.error(e.getMessage());
                loginFailureHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
            }

        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    /**
     * 校验验证码
     * @param request
     */
    private void validate(HttpServletRequest request){
        String key=request.getParameter("key");
        String code=request.getParameter("code");
        if(null==code||null==key){
            throw new CaptchaException("验证码不能为空");
        }
        if(!code.equals(redisUtil.hget(Const.CAPTCHA_KEY,key))){
            throw new CaptchaException("验证码错误");
        }
        //验证码使用一次后删除
        redisUtil.hdel(Const.CAPTCHA_KEY,key);
    }
}
