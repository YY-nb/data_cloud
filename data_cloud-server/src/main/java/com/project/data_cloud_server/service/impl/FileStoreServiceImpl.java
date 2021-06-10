package com.project.data_cloud_server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.data_cloud_server.entity.FileStore;
import com.project.data_cloud_server.mapper.FileStoreMapper;
import com.project.data_cloud_server.service.FileStoreService;
import org.springframework.stereotype.Service;

@Service
public class FileStoreServiceImpl extends ServiceImpl<FileStoreMapper, FileStore> implements FileStoreService {
    @Override
    public void createStore(String id) {
        FileStore fileStore=new FileStore();
        fileStore.setFileStoreId(id)
                .setUserId(id);
        save(fileStore);
    }
}
