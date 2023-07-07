package com.qiyuan.base2048.pojo.vo.query;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AttachmentQueryDTO {
    @NotBlank(message = "主GUID必填。")
    private String mainGuid;
    @NotBlank(message = "主表名必填。")
    private String mainTableName;
    @NotBlank(message = "类别必填。")
    private String mainStyle;
}
