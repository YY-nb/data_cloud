package com.project.data_cloud_server.config.security;

import com.project.data_cloud_server.entity.SysUser;
import com.project.data_cloud_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserService userService;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser user= userService.getUserByName(s);
        if(null==user){
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        return new AccountUser(user.getUsername(),user.getPassword());
    }
}
