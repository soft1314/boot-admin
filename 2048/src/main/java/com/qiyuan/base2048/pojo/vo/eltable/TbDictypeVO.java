package com.qiyuan.base2048.pojo.vo.eltable;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
* 数据字典类型表实体类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2022-11-7
*/

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "数据字典类型表实体类")
public class TbDictypeVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
    * 主键GUID
    */
    private String guid;
    /**
     *  编码
     */

    private String code;

    /**
     *  名称
     */

    private String name;

    /**
     *  短语
     */

    private String varName;

    /**
     *  备注
     */

    private String remarks;

    /**
     *  创建人
     */

    private String createBy;

    /**
     *  创建时间
     */

    private Date createTime;

    /**
     *  更新人
     */

    private String modifyBy;

    /**
     *  更新时间
     */

    private Date modifyTime;

    /**
     *  启用状态
     */

    private String enabled;

    /**
     *  是否删除
     */

    private String deleted;

    /**
     *  乐观锁
     */

    private BigDecimal version;

}