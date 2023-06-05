package com.qiyuan.base2048.log;

import com.qiyuan.base2048.mapper.mybatis.entity.TbLogAuth;

public interface AuthLogAsyncSaver {
    void asyncSave(TbLogAuth entity);
}
