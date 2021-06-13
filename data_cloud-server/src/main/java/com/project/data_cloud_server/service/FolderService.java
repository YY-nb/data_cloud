package com.project.data_cloud_server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.data_cloud_server.entity.Folder;
import com.project.data_cloud_server.entity.SysUser;

import java.util.List;

public interface FolderService extends IService<Folder> {
    public Folder getFolderByPath(String path);
    public List<Folder> getDirList(String pid,String fileStoreId);
    public void mkDir(String folderPath, SysUser user,String name,String pid);
}
