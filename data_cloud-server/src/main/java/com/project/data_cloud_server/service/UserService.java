package com.project.data_cloud_server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.data_cloud_server.entity.SysUser;

public interface UserService extends IService<SysUser> {
    SysUser getUserByName(String userName);
}
