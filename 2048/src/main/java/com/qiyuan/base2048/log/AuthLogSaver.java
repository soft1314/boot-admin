package com.qiyuan.base2048.log;

import com.qiyuan.bautil.enums.ResultTypeEnum;

public interface AuthLogSaver {
    void saveLog(String userName, ResultTypeEnum resultType, String msg);
}
