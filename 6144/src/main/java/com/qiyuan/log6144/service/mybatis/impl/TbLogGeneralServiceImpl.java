package com.qiyuan.log6144.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiyuan.log6144.mapper.mybatis.TbLogGeneralMapper;
import com.qiyuan.log6144.mapper.mybatis.entity.TbLogGeneral;
import com.qiyuan.log6144.service.mybatis.ITbLogGeneralService;
import org.springframework.stereotype.Service;

/**
* <p>
* 本文件自动生成，严禁人工修改
* 一般日志表 服务实现类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*/
@Service
public class TbLogGeneralServiceImpl extends ServiceImpl<TbLogGeneralMapper, TbLogGeneral> implements ITbLogGeneralService {
//    @Override
//    public boolean createLog(String logContent) throws Exception {
//        TbLogGeneral entity = new TbLogGeneral();
//        entity.setGuid(IdWorker.getIdStr(entity));
//        entity.setLogContent(StringTool.subStringDb(logContent,900));
//        entity.setDeleted(IsDeletedEnum.NOTDELETED.getStringValue());
//        entity.setEnabled(IsEnabledEnum.ENABLED.getStringValue());
//        entity.setVersion(1);
//        return this.save(entity);
//    }
}