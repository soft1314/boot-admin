package com.qiyuan.base2048.pojo.vo.oa;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MainContentVO {
    @NotBlank(message = "通知通告GUID是必填项。")
    private String notifyGuid;
    @NotBlank(message = "正文是必填项。")
    private String content;
}
