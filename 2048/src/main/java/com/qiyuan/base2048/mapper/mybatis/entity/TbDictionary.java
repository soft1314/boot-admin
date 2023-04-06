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
* 通用代码表实体类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2022-11-27 17:01:56
*/

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("TB_DICTIONARY")
@ApiModel(description = "通用代码表实体类")
public class TbDictionary implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
    * 主键
    */
    @TableId(value = "GUID", type = IdType.ASSIGN_ID)
    private String guid;
    /**
     *  编码
     */
    @TableField("ITEM_CODE")
    private String itemCode;
    /**
     *  名称
     */
    @TableField("ITEM_NAME")
    private String itemName;
    /**
     *  代码类别编码
     */
    @TableField("ITEM_TYPE_CODE")
    private String itemTypeCode;
    /**
     *  代码类别名称
     */
    @TableField("ITEM_TYPE_NAME")
    private String itemTypeName;
    /**
     *  记录创建时间
     */
    @TableField(value = "CREATE_TIME" ,fill = FieldFill.INSERT)
    private Date createTime;
    /**
     *  记录最后修改时间
     */
    @TableField(value = "MODIFY_TIME" ,fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;
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
     *  租户ID
     */
    @TableField("TENANT_ID_")
    private String tenantId_;
    /**
     *  备注
     */
    @TableField("REMARKS")
    private String remarks;
    /**
     *  乐观锁
     */
    @Version
    @TableField(value = "VERSION" ,fill = FieldFill.INSERT)
    private Integer version;
    /**
     *  顺序号
     */
    @TableField("SORT")
    private BigDecimal sort;
    /**
     *  是否末级;YESNO
     */
    @TableField("LEAF")
    private String leaf;
    /**
     *  父级GUID
     */
    @TableField("PARENT_GUID")
    private String parentGuid;
    /**
     *  记录创建者
     */
    @TableField(value = "CREATE_BY" ,fill = FieldFill.INSERT)
    private String createBy;
    /**
     *  记录最后修改者
     */
    @TableField(value = "MODIFY_BY" ,fill = FieldFill.INSERT_UPDATE)
    private String modifyBy;
    /**
     *  时间戳
     */
    @TableField("DATESTAMP")
    private Date datestamp;
}