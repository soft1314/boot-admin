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
* 行政区划表实体类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2022-11-24 8:59:30
*/

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("TB_ADM_DIV")
@ApiModel(description = "行政区划表实体类")
public class TbAdmDiv implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
    * 主键
    */
    @TableId(value = "GUID", type = IdType.ASSIGN_ID)
    private String guid;
    /**
     *  行政区划代码
     */
    @TableField("ADM_DIV_CODE")
    private String admDivCode;
    /**
     *  行政区划名称
     */
    @TableField("ADM_DIV_NAME")
    private String admDivName;
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
    /**
     *  父级GUID
     */
    @TableField("PARENT_GUID")
    private String parentGuid;
    /**
     *  是否末级;YESNO
     */
    @TableField("LEAF")
    private String leaf;
    /**
     *  顺序号
     */
    @TableField("SORT")
    private BigDecimal sort;
}