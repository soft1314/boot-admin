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
* 按人员通知关联表实体类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*/

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("TR_NOTIFY_EMP")
@ApiModel(description = "按人员通知关联表实体类")
public class TrNotifyEmp implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
    * 
    */
    @TableId(value = "GUID", type = IdType.ASSIGN_ID)
    private String guid;
    /**
     *  通知通告GUID
     */
    @TableField("NOTIFY_GUID")
    private String notifyGuid;
    /**
     *  人员GUID
     */
    @TableField("EMP_GUID")
    private String empGuid;
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