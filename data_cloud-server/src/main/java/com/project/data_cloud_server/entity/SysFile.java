package com.project.data_cloud_server.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_file")
public class SysFile implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件相对路径
     */
    private String path;

    /**
     * 创建人
     */
    @TableField("file_store_id")
    private String fileStoreId;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    private String pid;

    private Double size;

    private String type;


}
