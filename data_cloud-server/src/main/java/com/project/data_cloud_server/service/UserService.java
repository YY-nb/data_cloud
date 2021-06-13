package com.project.data_cloud_server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.data_cloud_server.common.api.ApiResult;
import com.project.data_cloud_server.entity.SysUser;

public interface UserService extends IService<SysUser> {
    SysUser getUserByName(String userName);

    SysUser getUserById(String userId);

    boolean checkName(String username);

    ApiResult register(SysUser user,String code);
}
