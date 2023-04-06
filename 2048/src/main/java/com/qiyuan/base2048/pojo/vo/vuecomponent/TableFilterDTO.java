package com.qiyuan.base2048.pojo.vo.vuecomponent;

import com.qiyuan.bautil.dto.PageQueryDTO;
import lombok.Data;

@Data
public class TableFilterDTO extends PageQueryDTO {
    private String tableName;
    private String tableType;
    private String tableComments;
    private String dataSource="master";
}
