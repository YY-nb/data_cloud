package com.project.data_cloud_server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.data_cloud_server.common.api.ApiResult;
import com.project.data_cloud_server.common.constValue.Const;
import com.project.data_cloud_server.entity.SysRole;
import com.project.data_cloud_server.entity.SysUser;
import com.project.data_cloud_server.entity.UserRole;
import com.project.data_cloud_server.mapper.SysRoleMapper;
import com.project.data_cloud_server.mapper.SysUserMapper;
import com.project.data_cloud_server.mapper.UserRoleMapper;
import com.project.data_cloud_server.service.FileStoreService;
import com.project.data_cloud_server.service.UserService;
import com.project.data_cloud_server.util.MailUtil;
import com.project.data_cloud_server.util.RedisUtil;
import com.project.data_cloud_server.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements UserService {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    FileStoreService fileStoreService;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Override
    public SysUser getUserByName(String userName) {
        return getOne(new QueryWrapper<SysUser>().eq("username",userName));
    }

    @Override
    public SysUser getUserById(String userId) {
        return getOne(new QueryWrapper<SysUser>().eq("user_id",userId));
    }

    @Override
    public boolean checkName(String username) {
        if(getOne(new QueryWrapper<SysUser>().eq("username",username))!=null){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    @Transactional
    public ApiResult register(SysUser user,String code) {
        String error;
        if(checkName(user.getUsername())){
            error="用户名已被注册";
            log.error(error);
            return ApiResult.error(error);
        }
        if(null==code||!code.equals(redisUtil.hget(Const.EMAIL_CODE,"code"))){
            error="邮箱验证码不正确";
            log.error(error);
            return ApiResult.error(error);
        }
        String id= UUIDUtil.getUUID();
        LocalDateTime dateTime=LocalDateTime.now();
        SysUser sysUser=new SysUser();
        sysUser.setUserId(id)
                .setFileStoreId(id)
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .setMobile(user.getMobile())
                .setPassword(new BCryptPasswordEncoder().encode(user.getPassword()))
                .setStatus(1)
                .setCreateTime(dateTime);
        save(sysUser);

        //初始化文件仓库
        fileStoreService.createStore(id);
        //初始化用户角色关系表
        UserRole userRole=new UserRole();
        userRole.setUserId(id)
                .setRoleId("1")
                .setId(UUIDUtil.getUUID());
        userRoleMapper.insert(userRole);
        log.info("用户注册成功");
        return ApiResult.success();
    }


}
