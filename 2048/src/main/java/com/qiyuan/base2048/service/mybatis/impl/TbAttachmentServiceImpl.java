package com.qiyuan.base2048.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TbAttachment;
import com.qiyuan.base2048.mapper.mybatis.TbAttachmentMapper;
import com.qiyuan.base2048.service.mybatis.ITbAttachmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* <p>
* 本文件自动生成，严禁人工修改
* 系统附件数据表 服务实现类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2023-5-9 14:25:25
*/
@Service
public class TbAttachmentServiceImpl extends ServiceImpl<TbAttachmentMapper, TbAttachment> implements ITbAttachmentService {
    @Override
    public List<TbAttachment> selectTbAttachmentByMainData(String tbName, String tbStyle, String tbGuid) throws Exception {
        QueryWrapper<TbAttachment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("P_TABLE_NAME",tbName)
                .eq("P_STYLE",tbStyle)
                .eq("P_GUID",tbGuid)
                .eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue())
                .orderByAsc("GUID");
        List<TbAttachment> tbAttachments = this.list(queryWrapper);
        return tbAttachments;
    }
}