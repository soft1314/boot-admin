package com.qiyuan.base2048.service.mybatis;

import com.qiyuan.base2048.mapper.mybatis.entity.TrNotifyDiv;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* <p>
* 本文件自动生成，严禁人工修改
* 按区划通知关联表 服务类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*/
public interface ITrNotifyDivService extends IService<TrNotifyDiv> {
    /**
     * 从表中查询某通知按区划通知的数据
     * @param notifyGuid
     * @return
     * @throws Exception
     */
    List<TrNotifyDiv> selectByNotifyGuid(String notifyGuid) throws Exception;
}