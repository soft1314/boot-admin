package com.qiyuan.base2048.service.common.impl;

import com.qiyuan.base2048.mapper.mapstruct.TbAttachmentStructMapper;
import com.qiyuan.base2048.mapper.mybatis.TbAttachmentMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TbAttachment;
import com.qiyuan.base2048.service.Component.MinIoUtils;
import com.qiyuan.base2048.service.common.MinioUploadService;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.MinIoFileDTO;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import com.qiyuan.bautil.util.UserTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@Service
public class MinioUploadServiceImpl implements MinioUploadService {
    @Autowired
    private MinIoUtils minIoUtils;
    @Resource
    private TbAttachmentMapper tbAttachmentMapper;

    @Override
    public ResultDTO saveAttach(MultipartFile multipartFile, String pGuid,String pTbname,String pStyle) throws Exception{
        String originalName = multipartFile.getOriginalFilename();
        String bucketName = minIoUtils.getMinioDefaultBucketName();
        String fileName = minIoUtils.getMinioDefaultFileName(originalName);
        minIoUtils.putObject(bucketName, multipartFile, fileName);
        TbAttachment tbAttachment = new TbAttachment();
        tbAttachment.setPGuid(pGuid);
        tbAttachment.setPStyle(pStyle);
        tbAttachment.setPTableName(pTbname);
        tbAttachment.setBucketName(bucketName);
        tbAttachment.setFileName(fileName);
        tbAttachment.setFileOriginName(originalName);
        tbAttachment.setContentType(multipartFile.getContentType());
        tbAttachment.setFileSize(new BigDecimal(multipartFile.getSize()).toString());
        String operator = UserTool.getOperator();
        tbAttachment.setCreateBy(operator);
        tbAttachment.setModifyBy(operator);
        Date now = new Date();
        tbAttachment.setCreateTime(now);
        tbAttachment.setModifyTime(now);
        tbAttachment.setEnabled(IsEnabledEnum.ENABLED.getStringValue());
        tbAttachment.setDeleted(IsDeletedEnum.NOTDELETED.getStringValue());
        tbAttachment.setVersion(0);
        int rtn = tbAttachmentMapper.insert(tbAttachment);
        if (rtn != 1) {
            return ResultDTO.failureCustom("插入附件信息数据时出错");
        }
        MinIoFileDTO minIoFileDTO = TbAttachmentStructMapper.INSTANCE.toMinIoFileDTO(tbAttachment);
        return ResultDTO.success(minIoFileDTO);
    }
}
