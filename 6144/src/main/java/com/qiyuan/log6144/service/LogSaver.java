package com.qiyuan.log6144.service;

import com.qiyuan.bautil.dto.LogDTO;

public interface LogSaver {
    void saveLog(LogDTO logDTO) throws Exception;
}
