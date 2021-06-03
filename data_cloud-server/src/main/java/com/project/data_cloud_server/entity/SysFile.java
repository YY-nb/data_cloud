package com.project.data_cloud_server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author axiuj
 * Create to 2021/6/3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_file")
public class SysFile implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文件名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 文件相对路径
     */
    @TableField(value = "path")
    private String path;

    /**
     * 创建人
     */
    @TableField(value = "creator")
    private Long creator;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "update_time")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_NAME = "name";

    public static final String COL_PATH = "path";

    public static final String COL_CREATOR = "creator";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";
}