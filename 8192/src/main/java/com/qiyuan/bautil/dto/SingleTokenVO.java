package com.qiyuan.bautil.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SingleTokenVO {
    @NotBlank(message = "Token是必填项")
    private String token;
}
