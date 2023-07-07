package com.qiyuan.base2048.service.manage;

import com.qiyuan.bautil.dto.BasePageQueryVO;
import com.qiyuan.bautil.dto.ResultDTO;

public interface OnlineUserService {
    ResultDTO pageOnlineUserVO(BasePageQueryVO basePageQueryVO) throws Exception ;
    ResultDTO listOnlineUserVO() throws Exception ;
}
