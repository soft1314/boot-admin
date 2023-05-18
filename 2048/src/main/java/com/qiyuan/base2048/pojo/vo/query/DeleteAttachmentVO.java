package com.qiyuan.base2048.pojo.vo.query;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DeleteAttachmentVO {
    @NotBlank(message = "GUID必填。")
    private String attachmentGuid;
    @NotBlank(message = "fileName必填。")
    private String fileName;
}
