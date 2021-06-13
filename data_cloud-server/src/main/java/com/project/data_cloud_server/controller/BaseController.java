package com.project.data_cloud_server.controller;

import com.project.data_cloud_server.service.FileService;
import com.project.data_cloud_server.service.FileStoreService;
import com.project.data_cloud_server.service.FolderService;
import com.project.data_cloud_server.service.UserService;
import com.project.data_cloud_server.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    UserService userService;
    @Autowired
    FileStoreService fileStoreService;
    @Autowired
    FileService fileService;
    @Autowired
    FolderService folderService;
}
