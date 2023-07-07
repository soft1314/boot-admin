package com.qiyuan.base2048.mapper.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("V_TABLES_MASTER")
public class VTablesMaster implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 名称
     */
    @TableField("TABLENAME")
    private String tablename;

    /**
     * 类别
     */
    @TableField("TABLETYPE")
    private String tabletype;

    /**
     * 描述
     */
    @TableField("COMMENTS")
    private String comments;
}
