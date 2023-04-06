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
@ApiModel(description = "流程查询")
public class ProcessQueryVO extends BasePageQueryVO {
    /**
     *  模型名称
     */
    private String name;
}

