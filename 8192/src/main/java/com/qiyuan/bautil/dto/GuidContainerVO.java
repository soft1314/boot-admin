package com.qiyuan.bautil.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GuidContainerVO {
    @NotBlank(message = "主键GUID不允许为空。")
    private String guid;
}
