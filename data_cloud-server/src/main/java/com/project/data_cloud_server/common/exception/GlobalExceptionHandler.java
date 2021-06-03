package com.project.data_cloud_server.common.exception;

import com.project.data_cloud_server.common.api.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ApiResult handle(RuntimeException e){
        log.error("运行时异常 {}",e.getMessage());
        return ApiResult.error(e.getMessage());
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ApiResult handler(AccessDeniedException e){
        log.error("security权限不足： {}",e.getMessage());
        return ApiResult.error(e.getMessage());
    }
}
