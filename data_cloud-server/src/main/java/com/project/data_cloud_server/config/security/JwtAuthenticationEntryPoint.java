package com.project.data_cloud_server.config.security;

import com.project.data_cloud_server.common.api.ApiResult;
import com.project.data_cloud_server.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        PrintWriter printWriter = JsonUtil.getPrintWriter(httpServletResponse);
        log.error("用户访问接口时未登录");
        ApiResult result=ApiResult.error("未登录，请先登录");
        JsonUtil.output(printWriter,result);
    }
}
