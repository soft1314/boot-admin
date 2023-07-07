package com.qiyuan.base2048.pojo.vo.save;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserOrgVO {
    @NotBlank(message = "userGuid是必填项。")
    private String userGuid;
    @NotBlank(message = "orgCode是必填项。")
    private String orgCode;
}
