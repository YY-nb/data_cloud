package com.project.data_cloud_server.common.exception;

import org.springframework.security.core.AuthenticationException;

public class CaptchaException extends AuthenticationException {
    public CaptchaException(String msg) {
        super(msg);
    }
}
