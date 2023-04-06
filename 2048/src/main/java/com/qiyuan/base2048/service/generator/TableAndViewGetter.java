package com.qiyuan.base2048.service.generator;

import com.qiyuan.base2048.pojo.vo.vuecomponent.TableFilterDTO;
import com.qiyuan.bautil.dto.ResultDTO;

public interface TableAndViewGetter {
    ResultDTO getPage(TableFilterDTO tableFilterDTO) throws Exception;
}
