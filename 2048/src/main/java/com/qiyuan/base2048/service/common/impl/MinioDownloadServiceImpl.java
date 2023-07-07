package com.qiyuan.base2048.service.common.impl;

import com.qiyuan.base2048.mapper.mybatis.TbAttachmentMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TbAttachment;
import com.qiyuan.base2048.service.component.MinIoUtils;
import com.qiyuan.base2048.service.common.MinioDownloadService;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Service
public class MinioDownloadServiceImpl implements MinioDownloadService {
    @Resource
    private TbAttachmentMapper tbAttachmentMapper;
    @Resource
    private MinIoUtils minIoUtils;

    @Override
    public void fetchMinioFile(String guid, HttpServletResponse response) throws Exception {
        TbAttachment tbAttachment = tbAttachmentMapper.selectById(guid);
        if(tbAttachment == null){
            throw new Exception("该文件不存在。");
        }
        if(IsDeletedEnum.DELETED.getStringValue().equals(tbAttachment.getDeleted())){
            throw new Exception("该文件已经删除");
        }
        if(IsEnabledEnum.NOTENABLED.getValue().equals(tbAttachment.getEnabled())){
            throw new Exception("该文件已经无效");
        }
        minIoUtils.downloadFile(tbAttachment.getBucketName(),tbAttachment.getFileName(),tbAttachment.getFileOriginName(),response);
    }
}
