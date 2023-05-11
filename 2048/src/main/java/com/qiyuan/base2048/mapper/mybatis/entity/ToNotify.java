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
* 通知通告主表实体类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2023-5-8 10:37:20
*/

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("TO_NOTIFY")
@ApiModel(description = "通知通告主表实体类")
public class ToNotify implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
    * 主键
    */
    @TableId(value = "GUID", type = IdType.ASSIGN_ID)
    private String guid;
    /**
     *  类别;NOTIFY_TYPE
     */
    @TableField("NOTIFY_TYPE")
    private String notifyType;
    /**
     *  标题
     */
    @TableField("TITLE")
    private String title;
    /**
     *  内容概要
     */
    @TableField("SUMMARY")
    private String summary;
    /**
     *  关键词
     */
    @TableField("KEYWORD")
    private String keyword;
    /**
     *  是否已发布;YESNO
     */
    @TableField("HAVE_RELEASE")
    private String haveRelease;
    /**
     *  发布时间
     */
    @TableField("RELEASE_TIME")
    private Date releaseTime;
    /**
     *  发布人
     */
    @TableField("RELEASE_BY")
    private String releaseBy;
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