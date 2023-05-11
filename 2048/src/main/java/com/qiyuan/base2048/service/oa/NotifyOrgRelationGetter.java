package com.qiyuan.base2048.service.oa;

import com.qiyuan.base2048.pojo.vo.query.NotifyOrgQueryVO;
import com.qiyuan.bautil.dto.ResultDTO;

public interface NotifyOrgRelationGetter {
    ResultDTO fetchGrantedPage(NotifyOrgQueryVO notifyOrgQueryVO) throws Exception;
    ResultDTO fetchGrantablePage(NotifyOrgQueryVO notifyOrgQueryVO) throws Exception;
}
