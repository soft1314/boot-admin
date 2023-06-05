package com.qiyuan.log6144.log;

import com.qiyuan.bautil.dto.SysOperLog;
import com.qiyuan.bautil.service.SysOperLogAsyncSaver;
import org.springframework.stereotype.Service;

@Service
public class SysOperLogAsyncSaverImpl implements SysOperLogAsyncSaver {
    @Override
    public void asyncSave(SysOperLog operLog) {
        //todo 暂未实现
    }
}
