package com.qiyuan.base2048.mapper.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiyuan.base2048.mapper.mybatis.entity.ToNotifyRecord;
import com.qiyuan.base2048.pojo.vo.query.MyNotifyQueryVO;
import com.qiyuan.base2048.pojo.vo.query.NotifyEmpQueryVO;
import com.qiyuan.base2048.pojo.vo.result.NotifyResultVO;
import com.qiyuan.base2048.pojo.vo.result.UserNotifyRecordVO;
import org.apache.ibatis.annotations.Param;

/**
* <p>
* 本文件自动生成，严禁人工修改
* 通知通告用户接收阅读表实体类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2023-5-9 14:24:58
*/
public interface ToNotifyRecordMapper extends BaseMapper<ToNotifyRecord> {
    /**
     * 按通知通告GUID以及查询条件，取已通知记录表
     * @param notifyEmpQueryVO
     * @return
     * @throws Exception
     */
    IPage<UserNotifyRecordVO> selectTbUserPageByNotifyGuid(Page<UserNotifyRecordVO> page, @Param("notifyEmpQueryVO") NotifyEmpQueryVO notifyEmpQueryVO) throws Exception;

    /**
     * 按用户GUID以及查询条件，取我的通知记录数据
     * @param page
     * @param myNotifyQueryVO
     * @return
     * @throws Exception
     */
    IPage<NotifyResultVO> selectTbUserPageByUserGuid(Page<NotifyResultVO> page, @Param("myNotifyQueryVO") MyNotifyQueryVO myNotifyQueryVO) throws Exception;
}