package com.project.data_cloud_server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.data_cloud_server.entity.SysUser;
import com.project.data_cloud_server.mapper.SysUserMapper;
import com.project.data_cloud_server.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements UserService {

    @Override
    public SysUser getUserByName(String userName) {
        return getOne(new QueryWrapper<SysUser>().eq("username",userName));
    }
}
