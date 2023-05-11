package com.qiyuan.base2048.service.oa;

import com.qiyuan.base2048.pojo.vo.query.NotifyEmpQueryVO;
import com.qiyuan.bautil.dto.ResultDTO;

public interface NotifyEmpRelationGetter {
    ResultDTO fetchGrantedPage(NotifyEmpQueryVO notifyEmpQueryVO) throws Exception;
    ResultDTO fetchGrantablePage(NotifyEmpQueryVO notifyEmpQueryVO) throws Exception;
}
