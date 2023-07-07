package com.qiyuan.base2048.pojo.vo.query;

import com.qiyuan.bautil.dto.PageQueryDTO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class NotifyEmpQueryVO extends PageQueryDTO {
    @NotBlank(message = "通知通告GUID必填。")
    private String notifyGuid;
    private String logonName;
    private String empName;
    private String admDivCode;
    @NotNull(message = "是否忽略区划必填。")
    private Boolean ignoreAdmDiv;
}
