package com.qiyuan.base2048.pojo.vo.query;

import com.qiyuan.bautil.dto.PageQueryDTO;
import lombok.Data;

@Data
public class NotifyOrgQueryVO extends PageQueryDTO {
    private String notifyGuid;
    private String orgCode;
    private String orgName;
    private String admDivCode;
    private Boolean ignoreAdmDiv;
}
