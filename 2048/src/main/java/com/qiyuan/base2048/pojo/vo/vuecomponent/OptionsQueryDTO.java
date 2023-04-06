package com.qiyuan.base2048.pojo.vo.vuecomponent;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class OptionsQueryDTO {
    @NotBlank(message = "数据集类别是必填项。")
    private String optionType;
}
