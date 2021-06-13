package com.project.data_cloud_server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.data_cloud_server.entity.FileStore;
import com.project.data_cloud_server.mapper.FileStoreMapper;
import com.project.data_cloud_server.service.FileStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FileStoreServiceImpl extends ServiceImpl<FileStoreMapper, FileStore> implements FileStoreService {
    @Override
    public void createStore(String id) {
        FileStore fileStore=new FileStore();
        fileStore.setFileStoreId(id)
                .setUserId(id);
        save(fileStore);
    }

    @Override
    public FileStore getStoreById(String id) {
        return getOne(new QueryWrapper<FileStore>().eq("file_store_id",id));
    }

    @Override
    public void updateSize(String id,Double size) {
        update(new UpdateWrapper<FileStore>().eq("file_store_id",id).set("current_size",size));
    }
}
