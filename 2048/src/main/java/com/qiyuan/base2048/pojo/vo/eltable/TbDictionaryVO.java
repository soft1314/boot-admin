package com.qiyuan.base2048.pojo.vo.eltable;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
* 通用代码表实体类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2022-11-1 9:59:13
*/

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "通用代码表实体类")
public class TbDictionaryVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
    * 主键GUID
    */
    private String guid;
    /**
     *  编码
     */

    private String itemCode;

    /**
     *  名称
     */

    private String itemName;

    /**
     *  代码类别编码
     */

    private String itemTypeCode;

    /**
     *  代码类别名称
     */

    private String itemTypeName;

    /**
     *  创建时间
     */

    private Date createTime;

    /**
     *  修改时间
     */

    private Date modifyTime;

    /**
     *  启用状态1有效2无效
     */

    private BigDecimal enabled;

    /**
     *  删除状态1未删除2删除
     */

    private BigDecimal deleted;

    /**
     *  备注
     */

    private String remarks;

    /**
     *  乐观锁
     */

    private BigDecimal version;

    /**
     *  显示顺序
     */

    private BigDecimal sort;
    /**
     *  是否末级
     */

    private String leaf;
    /**
     *  父级GUID
     */
    private String parentGuid;
}