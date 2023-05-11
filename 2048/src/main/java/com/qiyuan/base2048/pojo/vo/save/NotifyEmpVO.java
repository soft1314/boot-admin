package com.qiyuan.base2048.pojo.vo.save;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NotifyEmpVO {
    @NotBlank(message = "通知通告Guid是必填项。")
    private String notifyGuid;
    @NotBlank(message = "人员Guid是必填项。")
    private String empGuid;
}
