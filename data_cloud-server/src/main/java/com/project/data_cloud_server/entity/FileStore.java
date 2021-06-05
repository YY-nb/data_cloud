package com.project.data_cloud_server.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("file_store")
public class FileStore implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("file_store_id")
    private String fileStoreId;

    @TableField("user_id")
    private String userId;

    @TableField("current_size")
    private Double currentSize;

    @TableField("max_size")
    private Double maxSize;


}
