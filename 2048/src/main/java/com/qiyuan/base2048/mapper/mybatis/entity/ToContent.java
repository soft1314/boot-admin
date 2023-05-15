package com.qiyuan.base2048.mapper.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
* 本文件自动生成，严禁人工修改
* 通知通告正文实体类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2023-5-9 14:25:09
*/

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("TO_CONTENT")
@ApiModel(description = "通知通告正文实体类")
public class ToContent implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
    * 
    */
    @TableId(value = "GUID", type = IdType.INPUT)
    private String guid;
    /**
     *  正文
     */
    @TableField("CONTENT")
    private String content;
    /**
     *  记录创建者
     */
    @TableField(value = "CREATE_BY" ,fill = FieldFill.INSERT)
    private String createBy;
    /**
     *  记录创建时间
     */
    @TableField(value = "CREATE_TIME" ,fill = FieldFill.INSERT)
    private Date createTime;
    /**
     *  记录最后修改者
     */
    @TableField(value = "MODIFY_BY" ,fill = FieldFill.INSERT_UPDATE)
    private String modifyBy;
    /**
     *  记录最后修改时间
     */
    @TableField(value = "MODIFY_TIME" ,fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;
    /**
     *  时间戳
     */
    @TableField("DATESTAMP")
    private Date datestamp;
    /**
     *  启用状态;ENABLED
     */
    @TableField("ENABLED")
    private String enabled;
    /**
     *  删除状态;DELETED
     */
    @TableField("DELETED")
    private String deleted;
    /**
     *  乐观锁
     */
    @Version
    @TableField(value = "VERSION" ,fill = FieldFill.INSERT)
    private Integer version;
    /**
     *  备注
     */
    @TableField("REMARKS")
    private String remarks;
    /**
     *  租户ID
     */
    @TableField("TENANT_ID_")
    private String tenantId_;
}