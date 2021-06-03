package com.project.data_cloud_server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
@TableName(value = "sys_dict")
public class SysDict implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 字典类型
     */
    @TableField(value = "type")
    private String type;

    /**
     * 值
     */
    @TableField(value = "name")
    private String name;

    /**
     * key
     */
    @TableField(value = "code")
    private String code;

    /**
     * 排序字段
     */
    @TableField(value = "orderid")
    private Integer orderid;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_TYPE = "type";

    public static final String COL_NAME = "name";

    public static final String COL_CODE = "code";

    public static final String COL_ORDERID = "orderid";
}