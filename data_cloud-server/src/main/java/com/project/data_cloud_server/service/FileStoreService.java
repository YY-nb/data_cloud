package com.project.data_cloud_server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.data_cloud_server.entity.FileStore;

public interface FileStoreService extends IService<FileStore> {
    void createStore(String id);
    FileStore getStoreById(String id);
    void updateSize(String id,Double size);
}
