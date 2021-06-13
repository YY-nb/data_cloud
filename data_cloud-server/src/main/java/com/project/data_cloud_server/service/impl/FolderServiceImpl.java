package com.project.data_cloud_server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.data_cloud_server.entity.Folder;
import com.project.data_cloud_server.entity.SysUser;
import com.project.data_cloud_server.mapper.FolderMapper;
import com.project.data_cloud_server.service.FolderService;
import com.project.data_cloud_server.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class FolderServiceImpl extends ServiceImpl<FolderMapper, Folder>implements FolderService {
    @Override
    public Folder getFolderByPath(String path) {
        return getOne(new QueryWrapper<Folder>().eq("folder_path",path));
    }

    @Override
    public List<Folder> getDirList(String pid, String fileStoreId) {
        return list(new QueryWrapper<Folder>().eq("pid",pid).eq("file_store_id",fileStoreId));
    }

    @Override
    public void mkDir(String folderPath,SysUser user,String name,String pid) {
        new File(folderPath).mkdirs();
        log.info("文件夹在本地创建成功");
        Folder folder=new Folder();
        LocalDateTime dateTime=LocalDateTime.now();
        folder.setId(UUIDUtil.getUUID())
                .setFileStoreId(user.getFileStoreId())
                .setFolderPath(folderPath)
                .setName(name)
                .setCreateTime(dateTime)
                .setUpdateTime(dateTime)
                .setPid(pid);
        save(folder);

    }

}
