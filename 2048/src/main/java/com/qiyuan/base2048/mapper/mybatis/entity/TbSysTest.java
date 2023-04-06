package com.qiyuan.base2048.mapper.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
* <p>
* 测试表实体类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2021-12-14 23:08:59
*/

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("TB_SYS_TEST")
@ApiModel(description = "测试表实体类")
public class TbSysTest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
    * 主键
    */
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;
    /**
     *  GUID
     */
    @TableField("GUID")
    private String guid;
    /**
     *  姓名
     */
    @TableField("USER_NAME")
    private String userName;
    /**
     *  年龄
     */
    @TableField("AGE")
    private BigDecimal age;
    /**
     *  测试
     */
    @TableField("BLOB_TEST")
    private Object blobTest;
    /**
     *  瞄瞄
     */
    @TableField("TEST_CLOB")
    private String testClob;
    @TableField("A_B_C")
    private BigDecimal aBC;
    @TableField("T_DATE")
    private Date tDate;
    @TableField("TT_DATE")
    private Date ttDate;
    @TableField("AA")
    private String aa;
}