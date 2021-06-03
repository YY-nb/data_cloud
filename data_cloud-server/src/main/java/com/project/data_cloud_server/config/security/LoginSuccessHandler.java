package com.project.data_cloud_server.config.security;

import com.project.data_cloud_server.common.api.ApiResult;
import com.project.data_cloud_server.util.JsonUtil;
import com.project.data_cloud_server.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    /**
     * 登录成功返回token
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        PrintWriter printWriter= JsonUtil.getPrintWriter(httpServletResponse);
        Map<String,String> map=new HashMap<>();
        String token= jwtTokenUtil.generateToken(authentication.getName());
        map.put("token",token);
        ApiResult result=ApiResult.success("登录成功",map);
        JsonUtil.output(printWriter,result);
    }
}
