package com.qiyuan.base2048.service.mybatis;

import com.qiyuan.base2048.mapper.mybatis.entity.ToNotifyRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiyuan.base2048.pojo.vo.result.UserNotifyRecordVO;
import com.qiyuan.bautil.dto.ResultDTO;

import java.util.List;

/**
* <p>
* 本文件自动生成，严禁人工修改
* 通知通告用户接收阅读表 服务类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*/
public interface IToNotifyRecordService extends IService<ToNotifyRecord> {
    /**
     * 获取已发送LIST
     * @param notifyGuid
     * @return
     * @throws Exception
     */
    List<ToNotifyRecord> selectByNotifyGuid(String notifyGuid) throws Exception;

    /**
     * 设置已阅读
     * @param notifyGuid
     * @return
     * @throws Exception
     */
    ResultDTO setReaded(String notifyGuid) throws Exception;
}