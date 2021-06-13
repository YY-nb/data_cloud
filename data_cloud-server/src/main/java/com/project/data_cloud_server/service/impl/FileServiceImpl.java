package com.project.data_cloud_server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.data_cloud_server.common.constValue.Const;
import com.project.data_cloud_server.entity.SysFile;
import com.project.data_cloud_server.entity.SysUser;
import com.project.data_cloud_server.mapper.SysFileMapper;
import com.project.data_cloud_server.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@Transactional
public class FileServiceImpl extends ServiceImpl<SysFileMapper, SysFile>implements FileService {


    @Override
    public String getPidByPath(String path) {
        String pid;
        //分离出父文件夹的名字
        String folderName=path.substring(path.lastIndexOf("/"));
        if(folderName.equals("/")){
            pid="0"; //根目录id默认为0
        }
        else{
            pid=getOne(new QueryWrapper<SysFile>().eq("path",path)).getPid();
        }
        return pid;
    }

    @Override
    public void upload(String path, SysFile file,MultipartFile multipartFile) throws IOException {
        makeDir( path);
        multipartFile.transferTo(new File(path,multipartFile.getOriginalFilename()));
        save(file);
    }

    @Override
    public List<SysFile> getFileList(String pid,String fileStoreId) {
        return list(new QueryWrapper<SysFile>().eq("pid",pid).eq("file_store_id",fileStoreId));
    }

    @Override

    public Double deleteFiles(List<String> paths,String userName) {
        Double deleteSize=0.0;
        for(String path:paths){
            String filePath= Const.FORMER_PATH+userName+path;
            QueryWrapper<SysFile> wrapper=new QueryWrapper<SysFile>().eq("path",filePath);
            Double fileSize=getOne(wrapper).getSize();
            deleteSize+=fileSize;
            File file=new File(filePath);
            file.delete();
            remove(wrapper);
        }
        return deleteSize;
    }


    private void makeDir(String path) {
        File folder=new File(path);
        if(!folder.isDirectory()){
            folder.mkdirs();
            log.info("文件夹在服务器上上传成功");
        }
    }


}
