package com.project.data_cloud_server.config.security;

import com.project.data_cloud_server.common.api.ApiResult;
import com.project.data_cloud_server.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Slf4j
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        if(authentication!=null){
            new SecurityContextLogoutHandler().logout(httpServletRequest,httpServletResponse,authentication);
        }
        PrintWriter printWriter = JsonUtil.getPrintWriter(httpServletResponse);
        ApiResult result= ApiResult.success("退出成功",null);
        log.info("用户已退出");
        JsonUtil.output(printWriter,result);
    }
}
