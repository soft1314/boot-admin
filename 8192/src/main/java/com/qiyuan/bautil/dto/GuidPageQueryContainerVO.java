package com.qiyuan.bautil.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GuidPageQueryContainerVO extends BasePageQueryVO{
    @NotBlank(message = "主键GUID不允许为空。")
    private String guid;
}
