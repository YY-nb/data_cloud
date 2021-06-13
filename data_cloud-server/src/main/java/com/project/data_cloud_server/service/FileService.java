package com.project.data_cloud_server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.data_cloud_server.entity.SysFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface FileService extends IService<SysFile> {
    public  String getPidByPath(String path);
    public void upload(String path, SysFile file, MultipartFile multipartFile) throws IOException;

    public List<SysFile> getFileList(String pid,String fileStoreId);
    public Double deleteFiles(List<String> paths,String userName);
}
