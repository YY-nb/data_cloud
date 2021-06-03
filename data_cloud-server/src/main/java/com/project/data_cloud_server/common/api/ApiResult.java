package com.project.data_cloud_server.common.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResult {
    private int code;
    private String message;
    private Object data;
    public static ApiResult success(){
        return new ApiResult(ApiCode.SUCCESS.getCode(),ApiCode.SUCCESS.getMessage(),null);
    }

    /**
     * 自定义message,data
     * @param message
     * @return
     */
    public static ApiResult success(String message,Object data){
        return new ApiResult(ApiCode.SUCCESS.getCode(),message,data);
    }

    /**
     * 自定义data
     * @param data
     * @return
     */
    public static ApiResult success(Object data){
        return new ApiResult(ApiCode.SUCCESS.getCode(),ApiCode.SUCCESS.getMessage(),data);
    }

    /**
     * 自定义message
     * @param message
     * @return
     */
    public static ApiResult error(String message){
        return new ApiResult(ApiCode.FAIL.getCode(), message,null);
    }

    /**
     * 自定义message,data
     * @param message
     * @param data
     * @return
     */
    public static ApiResult error(String message,Object data){
        return new ApiResult(ApiCode.FAIL.getCode(), message,data);
    }
}
