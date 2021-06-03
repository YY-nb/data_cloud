package com.project.data_cloud_server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
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
@TableName(value = "tagging")
public class Tagging implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文件夹_id
     */
    @TableField(value = "folder_id")
    private Integer folderId;

    /**
     * 注释
     */
    @TableField(value = "anno")
    private String anno;

    /**
     * 定位点
     */
    @TableField(value = "location")
    private BigDecimal location;

    /**
     * 创建人
     */
    @TableField(value = "creator")
    private Long creator;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_FOLDER_ID = "folder_id";

    public static final String COL_ANNO = "anno";

    public static final String COL_LOCATION = "location";

    public static final String COL_CREATOR = "creator";
}