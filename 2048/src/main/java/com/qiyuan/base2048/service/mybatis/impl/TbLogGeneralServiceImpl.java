package com.qiyuan.base2048.service.mybatis.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.qiyuan.base2048.mapper.mybatis.entity.TbLogGeneral;
import com.qiyuan.base2048.mapper.mybatis.TbLogGeneralMapper;
import com.qiyuan.base2048.service.mybatis.ITbLogGeneralService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import org.springframework.stereotype.Service;

/**
* <p>
* 本文件自动生成，严禁人工修改
* 一般日志表 服务实现类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2023-3-14 11:20:46
*/
@Service
public class TbLogGeneralServiceImpl extends ServiceImpl<TbLogGeneralMapper, TbLogGeneral> implements ITbLogGeneralService {
    @Override
    public boolean createLog(String logContent) throws Exception {
        TbLogGeneral entity = new TbLogGeneral();
        entity.setGuid(IdWorker.getIdStr(entity));
        entity.setLogContent(logContent);
        entity.setDeleted(IsDeletedEnum.NOTDELETED.getStringValue());
        entity.setEnabled(IsEnabledEnum.ENABLED.getStringValue());
        entity.setVersion(1);
        return this.save(entity);
    }
}