package com.project.data_cloud_server.config.security;

import com.project.data_cloud_server.common.api.ApiResult;
import com.project.data_cloud_server.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
@Component
@Slf4j
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        PrintWriter printWriter= JsonUtil.getPrintWriter(httpServletResponse);
        String error;
        if(e instanceof BadCredentialsException){
            error="用户名或密码错误";

        }
        else {
            error=e.getMessage();
        }
        log.error(error);
        ApiResult result=ApiResult.error(error);
        JsonUtil.output(printWriter,result);
    }
}
