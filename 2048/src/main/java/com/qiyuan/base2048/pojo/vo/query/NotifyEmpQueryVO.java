package com.qiyuan.base2048.pojo.vo.query;

import com.qiyuan.bautil.dto.PageQueryDTO;
import lombok.Data;

@Data
public class NotifyEmpQueryVO extends PageQueryDTO {
    private String notifyGuid;
    private String logonName;
    private String empName;
    private String admDivCode;
    private Boolean ignoreAdmDiv;
}
