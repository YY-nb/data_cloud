package com.project.data_cloud_server.config.security;


import com.project.data_cloud_server.common.api.ApiResult;
import com.project.data_cloud_server.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 当访问的接口没有权限时，自定义返回结果
 */
@Component
@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        PrintWriter printWriter = JsonUtil.getPrintWriter(httpServletResponse);
        log.error("用户无权访问");
        ApiResult result=ApiResult.error("权限不足无法访问");
        JsonUtil.output(printWriter,result);
    }
}
