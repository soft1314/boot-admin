package com.qiyuan.base2048.service.common.impl;

import com.qiyuan.base2048.mapper.mybatis.TbAttachmentMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TbAttachment;
import com.qiyuan.base2048.service.common.AttachmentSaver;
import com.qiyuan.base2048.service.mybatis.ITbAttachmentService;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import com.qiyuan.bautil.util.UserTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class AttachmentSaverImpl implements AttachmentSaver {
    @Resource
    private TbAttachmentMapper tbAttachmentMapper;
    @Resource
    private ITbAttachmentService iTbAttachmentService;

    @Override
    public TbAttachment genTbAttachment(String mainGuid, String mainStyle, String mainTableName, String bucketName, String fileName, String originalName, String contentType, String fileSize) {
        TbAttachment tbAttachment = new TbAttachment();
        tbAttachment.setPGuid(mainGuid);
        tbAttachment.setPStyle(mainStyle);
        tbAttachment.setPTableName(mainTableName);
        tbAttachment.setBucketName(bucketName);
        tbAttachment.setFileName(fileName);
        tbAttachment.setFileOriginName(originalName);
        tbAttachment.setContentType(contentType);
        tbAttachment.setFileSize(fileSize);
        String operator = UserTool.getOperator();
        tbAttachment.setCreateBy(operator);
        tbAttachment.setModifyBy(operator);
        Date now = new Date();
        tbAttachment.setCreateTime(now);
        tbAttachment.setModifyTime(now);
        tbAttachment.setEnabled(IsEnabledEnum.ENABLED.getStringValue());
        tbAttachment.setDeleted(IsDeletedEnum.NOTDELETED.getStringValue());
        tbAttachment.setVersion(0);
        return tbAttachment;
    }

    @Override
    public int saveTbAttachment(TbAttachment tbAttachment) throws Exception {
        int rtn = tbAttachmentMapper.insert(tbAttachment);
        return rtn;
    }

    @Override
    public ResultDTO removeAttachment(String guid, String fileName) throws Exception {
        if(iTbAttachmentService.deleteAttachment(guid,fileName)){
            return ResultDTO.success();
        }
        return ResultDTO.failure();
    }
}
