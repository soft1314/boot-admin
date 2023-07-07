package com.qiyuan.base2048.log;

import com.qiyuan.base2048.mapper.mybatis.TbLogAuthMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TbLogAuth;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AuthLogAsyncSaverImpl implements AuthLogAsyncSaver{
    @Resource
    private TbLogAuthMapper tbLogAuthMapper;

    @Override
    @Async
    public void asyncSave(TbLogAuth entity) {
        tbLogAuthMapper.insert(entity);
    }
}
