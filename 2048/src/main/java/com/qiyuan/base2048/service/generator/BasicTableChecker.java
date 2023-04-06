package com.qiyuan.base2048.service.generator;

import com.qiyuan.base2048.pojo.vo.vuecomponent.UserTableColumnResultDTO;
import com.qiyuan.bautil.dto.ResultDTO;

import java.util.List;

public interface BasicTableChecker {
    ResultDTO checkCols(List<UserTableColumnResultDTO> userTableColumnResultDTOList) throws Exception;
}
