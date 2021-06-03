package com.project.data_cloud_server.common.api;


public enum ApiCode {
    SUCCESS(200,"操作成功"),
    FAIL(400,"操作失败");

    private final int code;
    private final String message;

    ApiCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
