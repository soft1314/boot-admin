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
* 示例表（层次CRUD）单位表实体类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2023-4-12 22:25:47
*/

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("DEMO_ORG")
@ApiModel(description = "示例表（层次CRUD）单位表实体类")
public class DemoOrg implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
    * 主键
    */
    @TableId(value = "GUID", type = IdType.ASSIGN_ID)
    private String guid;
    /**
     *  单位名称
     */
    @TableField("NAME")
    private String name;
    /**
     *  社会信用代码
     */
    @TableField("CODE")
    private String code;
    /**
     *  组织类别;ORGTYPE
     */
    @TableField("ORGTYPE")
    private String orgtype;
    /**
     *  行业类别;INDUSTRY_CATEGORY;LAZY
     */
    @TableField("INDUSTRY_CATEGORY")
    private String industryCategory;
    /**
     *  成立时间
     */
    @TableField("FOUND_TIME")
    private Date foundTime;
    /**
     *  单位级别;UNITLEVEL
     */
    @TableField("RANK_CODE")
    private String rankCode;
    /**
     *  编制人数
     */
    @TableField("NUM")
    private String num;
    /**
     *  办公地址
     */
    @TableField("ADDR")
    private String addr;
    /**
     *  负责人
     */
    @TableField("LEADER")
    private String leader;
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
     *  顺序号
     */
    @TableField("SORT")
    private String sort;
}