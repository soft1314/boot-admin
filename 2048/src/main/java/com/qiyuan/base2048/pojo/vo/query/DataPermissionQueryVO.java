package com.qiyuan.base2048.pojo.vo.query;

import com.qiyuan.bautil.dto.PageQueryDTO;
import lombok.Data;

@Data
public class DataPermissionQueryVO extends PageQueryDTO {
    private String guid;
    private String code;
    private String name;
}
