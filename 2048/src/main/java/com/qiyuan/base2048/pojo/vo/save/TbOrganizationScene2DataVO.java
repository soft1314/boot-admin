package com.qiyuan.base2048.pojo.vo.save;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 本文件自动生成，严禁人工修改
 * 组织机构基础表实体类
 * </p>
 *
 * @author 虚领顶劲气沉丹田
 * @since 2022-11-29 12:19:49
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "组织机构基础表实体类")
public class TbOrganizationScene2DataVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private String guid;
    /**
     * 机构编码
     */
    private String code;
    /**
     * 机构名称
     */
    private String name;
    /**
     * 所属区划
     */
    private String divCode;
    /**
     * 所属区划名称
     */
    private String divName;
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
     * 是否末级;YESNO
     */
    private String leaf;
    /**
     * 父级GUID
     */
    private String parentGuid;
    /**
     * 顺序号
     */
    private BigDecimal sort;
    /**
     * 是否末级
     **/
    private boolean hasChildren;
}