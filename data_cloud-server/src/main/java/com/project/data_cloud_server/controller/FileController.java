package com.project.data_cloud_server.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.project.data_cloud_server.common.api.ApiResult;
import com.project.data_cloud_server.common.constValue.Const;
import com.project.data_cloud_server.entity.FileStore;
import com.project.data_cloud_server.entity.Folder;
import com.project.data_cloud_server.entity.SysFile;
import com.project.data_cloud_server.entity.SysUser;
import com.project.data_cloud_server.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@Transactional
public class FileController extends BaseController{
    @PostMapping("/upload")
    public ApiResult upload(@RequestParam("path")String path,
                            @RequestParam("file")MultipartFile file) throws IOException {
        if(null==file){
            return ApiResult.error("未上传文件");
        }
        String error;
        String username= getNameFromContext();
        log.info("userName: {}",username);
        SysUser user= userService.getUserByName(username);
        SysFile sysFile=new SysFile();
        LocalDateTime dateTime=LocalDateTime.now();
        //判断是否超出文件仓库容量
        Double fileSize= Double.valueOf(file.getSize())/1024.0;
        FileStore fileStore=fileStoreService.getStoreById(user.getUserId());
        fileStore.setCurrentSize(fileStore.getCurrentSize()+fileSize);
        if(fileStore.getCurrentSize()>fileStore.getMaxSize()){
            error="文件仓库容量不足上传失败";
            log.error(error);
            return ApiResult.error(error);
        }
        String fileName=file.getOriginalFilename();
        String folderPath=Const.FORMER_PATH+user.getUsername()+path;
        sysFile.setFileStoreId(user.getUserId())
                .setId(UUIDUtil.getUUID())
                .setCreateTime(dateTime)
                .setUpdateTime(dateTime)
                .setName(fileName)
                .setPath(folderPath+fileName)
                .setPid(fileService.getPidByPath(path))
                .setSize(fileSize)
                .setType(file.getContentType());
        fileService.upload(folderPath,sysFile,file);
        log.info("文件上传成功,文件名：{},上传至{}文件夹",fileName,folderPath);
        fileStoreService.updateSize(fileStore.getFileStoreId(),fileStore.getCurrentSize());
        log.info("用户文件仓库已更新");
        return ApiResult.success();
    }


    @PostMapping("/getFileList")
    public ApiResult getFileList(@RequestBody JSONObject jsonObject) {
        String userName=getNameFromContext();
        String pid=getPid(jsonObject,userName);
        SysUser user= userService.getUserByName(userName);
        //根据id查询当前文件夹下的文件列表
        List<SysFile> fileList= fileService.getFileList(pid,user.getFileStoreId());

        return ApiResult.success(fileList);
    }
    @PostMapping("/getDirList")
    public ApiResult getDirList(@RequestBody JSONObject jsonObject){
        String userName=getNameFromContext();
        String pid=getPid(jsonObject,userName);
        SysUser user= userService.getUserByName(userName);
        List<Folder> dirList= folderService.getDirList(pid,user.getFileStoreId());
        return ApiResult.success(dirList);
    }
    private String getNameFromContext(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
    private String getPid(JSONObject jsonObject,String userName){
        String path=jsonObject.get("path").toString();
        String pid;
        if(path.substring(path.lastIndexOf("/")).equals("/")){
            pid="0";
        }
        else{
            String folderPath= Const.FORMER_PATH+userName+path;
            //根据路径得到父文件夹id
            Folder folder= folderService.getFolderByPath(folderPath);
            pid= folder.getId();
        }
        return pid;
    }
    private String getPid(String folderPath){
        String pid;
        if(folderPath.substring(folderPath.lastIndexOf("/")).equals("/")){
            pid="0";
        }
        else{
            //根据路径得到父文件夹id
            Folder folder= folderService.getFolderByPath(folderPath);
            pid= folder.getId();
        }
        return pid;
    }
    @PostMapping("/deleteFile")
    public ApiResult deleteFiles(@RequestBody JSONObject jsonObject){
        String userName=getNameFromContext();
        JSONArray files = jsonObject.getJSONArray("files");
        List<String> paths = files.toJavaList(String.class);
        Double deleteSize= fileService.deleteFiles(paths,userName);
        //修改文件仓库当前容量
        SysUser user=new SysUser();
        FileStore store = fileStoreService.getStoreById(user.getUserId());
        store.setCurrentSize(store.getCurrentSize()-deleteSize);
        Double currentSize=store.getCurrentSize();
        fileStoreService.updateSize(user.getUserId(),currentSize);
        return ApiResult.success();
    }
    @PostMapping("/mkDir")
    public ApiResult mkDir(@RequestBody JSONObject jsonObject){
        String userName=getNameFromContext();
        String name=jsonObject.get("name").toString();
        String folderPath=Const.FORMER_PATH+userName+jsonObject.get("path").toString();
        SysUser user=userService.getUserByName(userName);
        folderService.mkDir(folderPath,user,name,getPid(folderPath));
        return ApiResult.success();
    }
}
