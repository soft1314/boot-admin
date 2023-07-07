package com.qiyuan.base2048.pojo.vo.vuecomponent;

import com.qiyuan.bautil.enums.DataTypeEnum;
import com.qiyuan.bautil.enums.TableColStyleEnum;
import lombok.Data;

@Data
public class TableColVO {
    private TableColStyleEnum type;
    private String label;
    private String prop;
    private String dicType;
    private DataTypeEnum dataType;
}
