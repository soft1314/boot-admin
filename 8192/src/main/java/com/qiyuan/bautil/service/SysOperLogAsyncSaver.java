package com.qiyuan.bautil.service;

import com.qiyuan.bautil.dto.SysOperLog;

/**
 * 异步保存日志接口
 */
public interface SysOperLogAsyncSaver {
    void asyncSave(SysOperLog operLog);
}
