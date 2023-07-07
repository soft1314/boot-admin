package com.qiyuan.base2048.pojo.vo.save;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserDivVO {
    @NotBlank(message = "userGuid是必填项。")
    private String userGuid;
    @NotBlank(message = "divCode是必填项。")
    private String divCode;
}
