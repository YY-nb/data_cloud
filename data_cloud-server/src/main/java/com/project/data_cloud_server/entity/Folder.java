package com.project.data_cloud_server.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Folder implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String pid;

    /**
     * 文件或文件夹名称
     */
    private String name;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件id  空表示是文件夹
     */
    @TableField("file_id")
    private Integer fileId;

    /**
     * 创建人
     */
    @TableField("file_stor_id")
    private String fileStorId;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;


}
