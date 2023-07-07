package com.qiyuan.base2048.pojo.vo.query;

import lombok.Data;
import com.qiyuan.bautil.dto.BasePageQueryVO;
import javax.validation.constraints.NotBlank;

@Data
public class TbDictionaryQueryVO extends BasePageQueryVO{
    @NotBlank(message = "代码类型是必填项。")
    private String itemTypeCode;
}
