package com.qiyuan.base2048.pojo.vo.oa;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class NotifyAdmDivRelationshipVO {
    @NotBlank(message = "通知通告的GUID是必填项。")
    private String notifyGuid;
    @NotNull(message = "勾选数组不允许NULL值。")
    private List<String> admDivCodes;
}
