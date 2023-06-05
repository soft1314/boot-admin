package com.qiyuan.base2048.pojo.vo.workflow;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 本文件自动生成，严禁人工修改
 * 工作流示例表1实体类
 * </p>
 *
 * @author 虚领顶劲气沉丹田
 * @since 2000-08-20
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "工作流示例表1实体类")
public class WfDemo1Scene3StartProcessDataVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private String guid;
    /**
     * 标题
     */
    private String title;
    /**
     * 报销金额
     */
    private BigDecimal money;
    /**
     * 情况说明
     */
    private String note;
    /**
     * 业务日期
     */
    private Date bizDate;
    /**
     * 启用状态;ENABLED
     */
    private String enabled;
    /**
     * 删除状态;DELETED
     */
    private String deleted;
    /**
     * 乐观锁
     */
    private BigDecimal version;
    /**
     * 备注
     */
    private String remarks;
    /**
     *  职称;TECH_TITLE;LAZY
     */
    private String techTitle;
    /**
     *  区划权限类型;ADM_DIV_PERMISSION
     */
    private String divPermission;

    /**
     * ------------------流程定义KEY--------------------
     */
    String procDefKey;
}