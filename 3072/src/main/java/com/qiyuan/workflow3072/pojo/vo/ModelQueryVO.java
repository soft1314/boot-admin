package com.qiyuan.workflow3072.pojo.vo;

import com.qiyuan.bautil.dto.BasePageQueryVO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 本文件自动生成，严禁人工修改
 * model实体类
 * </p>
 *
 * @author 虚领顶劲气沉丹田
 * @since 2023-2-28 10:27:21
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "model实体类")
public class ModelQueryVO extends BasePageQueryVO {

    /**
     * 主键
     */
    private String guid;
    /**
     *  模型标识
     */
    private String code;
    /**
     *  模型名称
     */
    private String name;
    /**
     *  乐观锁
     */
    private BigDecimal version;
    /**
     *  版本号
     */
    private BigDecimal ver;
    /**
     *  记录创建者
     */
    private String createBy;
    /**
     *  记录创建时间
     */
    private Date createTime;
    /**
     *  记录最后修改者
     */
    private String modifyBy;
    /**
     *  记录最后修改时间
     */
    private Date modifyTime;
    /**
     *  启用状态;ENABLED
     */
    private String enabled;
    /**
     *  删除状态;DELETED
     */
    private String deleted;
    /**
     *  备注
     */
    private String remarks;
}

