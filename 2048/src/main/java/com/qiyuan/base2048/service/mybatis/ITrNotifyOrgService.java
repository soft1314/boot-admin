package com.qiyuan.base2048.service.mybatis;

import com.qiyuan.base2048.mapper.mybatis.entity.TrNotifyOrg;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* <p>
* 本文件自动生成，严禁人工修改
* 按单位通知关联表 服务类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*/
public interface ITrNotifyOrgService extends IService<TrNotifyOrg> {
    /**
     * 获取需要发送的机构LIST
     * @param notifyGuid
     * @return
     * @throws Exception
     */
    List<TrNotifyOrg> getTrNotifyOrgList(String notifyGuid) throws Exception;
}