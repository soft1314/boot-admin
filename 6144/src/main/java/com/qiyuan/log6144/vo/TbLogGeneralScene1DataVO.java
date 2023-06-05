package com.qiyuan.log6144.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 本文件自动生成，严禁人工修改
 * 一般日志表实体类
 * </p>
 *
 * @author 虚领顶劲气沉丹田
 * @since 2000-08-20
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "一般日志表实体类")
public class TbLogGeneralScene1DataVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private String guid;
    /**
     *  日志内容
     */
    private String logContent;
    /**
     *  启用状态;ENABLED
     */
    private String enabled;
    /**
     *  删除状态;DELETED
     */
    private String deleted;
    /**
     *  乐观锁
     */
    private BigDecimal version;
    /**
     *  备注
     */
    private String remarks;
}
