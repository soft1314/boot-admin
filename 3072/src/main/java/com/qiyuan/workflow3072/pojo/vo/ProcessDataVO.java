package com.qiyuan.workflow3072.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "流程数据")
public class ProcessDataVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String deploymentId;
    private String category;
    private String key;
    private String description;
    private Integer version;
}