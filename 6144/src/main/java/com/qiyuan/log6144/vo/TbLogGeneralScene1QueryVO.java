package com.qiyuan.log6144.vo;

import com.qiyuan.bautil.dto.BasePageQueryVO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 本文件自动生成，严禁人工修改
 * 一般日志表实体类
 * </p>
 *
 * @author 虚领顶劲气沉丹田
 * @since 2023-4-11
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "一般日志表实体类")
public class TbLogGeneralScene1QueryVO extends BasePageQueryVO {

    /**
     * 主键
     */
    private String guid;
    /**
     * 日志级别
     */
    private String logLevel;
    /**
     * 类名
     */
    private String className;
    /**
     * 行号
     */
    private String lineNo;
    /**
     * 操作者
     */
    private String operator;
    /**
     * 日志内容
     */
    private String logContent;
    /**
     * 记录创建者
     */
    private String createBy;
    /**
     * 记录创建时间
     */
    private Date createTime;
    /**
     * 记录最后修改者
     */
    private String modifyBy;
    /**
     * 记录最后修改时间
     */
    private Date modifyTime;
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
}