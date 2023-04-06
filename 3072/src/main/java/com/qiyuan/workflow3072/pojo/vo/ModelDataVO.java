package com.qiyuan.workflow3072.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "model实体类")
public class ModelDataVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 模型标识
     */
    private String key;
    /**
     * 模型名称
     */
    private String name;
    private String description;
}