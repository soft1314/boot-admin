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
* 数据字典类型表实体类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*/

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("TB_DICTYPE")
@ApiModel(description = "数据字典类型表实体类")
public class TbDictype implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
    * 主键GUID
    */
    @TableId(value = "GUID", type = IdType.ASSIGN_ID)
    private String guid;
    /**
     *  编码
     */
    @TableField("CODE")
    private String code;
    /**
     *  名称
     */
    @TableField("NAME")
    private String name;
    /**
     *  短语
     */
    @TableField("VAR_NAME")
    private String varName;
    /**
     *  备注
     */
    @TableField("REMARKS")
    private String remarks;
    /**
     *  创建人
     */
    @TableField(value = "CREATE_BY" ,fill = FieldFill.INSERT)
    private String createBy;
    /**
     *  创建时间
     */
    @TableField(value = "CREATE_TIME" ,fill = FieldFill.INSERT)
    private Date createTime;
    /**
     *  更新人
     */
    @TableField(value = "MODIFY_BY" ,fill = FieldFill.INSERT_UPDATE)
    private String modifyBy;
    /**
     *  更新时间
     */
    @TableField(value = "MODIFY_TIME" ,fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;
    /**
     *  时间戳
     */
    @TableField("DATESTAMP")
    private Date datestamp;
    /**
     *  启用状态
     */
    @TableField("ENABLED")
    private String enabled;
    /**
     *  是否删除
     */
    @TableField("DELETED")
    private String deleted;
    /**
     *  乐观锁
     */
    @Version
    @TableField(value = "VERSION" ,fill = FieldFill.INSERT_UPDATE)
    private Integer version;
    /**
     *  租户号
     */
    @TableField("TENANT_ID_")
    private String tenantId_;
}