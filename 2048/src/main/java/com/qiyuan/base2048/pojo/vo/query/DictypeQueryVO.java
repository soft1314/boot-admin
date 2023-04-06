package com.qiyuan.base2048.pojo.vo.query;

import lombok.Data;
import com.qiyuan.bautil.dto.BasePageQueryVO;
@Data
public class DictypeQueryVO  extends BasePageQueryVO{
    private String code;
    private String name;
    private String varName;
    private String enabled;
}
