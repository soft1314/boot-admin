package com.qiyuan.base2048.pojo.vo.save;


import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 本文件自动生成，严禁人工修改
 * 系统规则表实体类
 * </p>
 *
 * @author 虚领顶劲气沉丹田
 * @since 2023-4-10 16:10:54
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "系统规则表实体类")
public class TbDroolsRuleScene1DataVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private String guid;
    /**
     * 规则名称
     */
    private String name;
    /**
     * 规则
     */
    private String rule;
    /**
     * 行政区划编码
     */
    private String admDivCode;
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
    private String version;
    /**
     * 备注
     */
    private String remarks;
}
