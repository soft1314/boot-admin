package com.qiyuan.bautil.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
@Data
public class MinIoFileDTO {
    @NotBlank(message = "桶名称是必填项。")
    private String bucketName;
    @NotBlank(message = "保存的文件名称是必填项。")
    private String fileName;
    @NotBlank(message = "原始文件名称是必填项。")
    private String fileOriginName;
    @NotBlank(message = "文件内容类型是必填项。")
    private String contentType;
    private BigDecimal fileSize;
    private String guid;
    private String name;
}
