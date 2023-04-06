package com.qiyuan.bautil.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DoubleTokenVO {
    @NotBlank(message = "firstToken是必填项")
    private String firstToken;
    @NotBlank(message = "secondToken是必填项")
    private String secondToken;
}
