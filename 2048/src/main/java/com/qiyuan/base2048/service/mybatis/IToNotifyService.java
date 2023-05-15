package com.qiyuan.base2048.service.mybatis;

import com.qiyuan.base2048.mapper.mybatis.entity.ToNotify;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* <p>
* 本文件自动生成，严禁人工修改
* 通知通告主表 服务类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2023-5-8 10:37:20
*/
public interface IToNotifyService extends IService<ToNotify> {
    void changeToPublished(String notifyGuid) throws Exception;
}