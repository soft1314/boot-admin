package com.qiyuan.base2048.service.common.impl;

import com.qiyuan.base2048.mapper.mapstruct.TbAttachmentStructMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TbAttachment;
import com.qiyuan.base2048.service.component.MinIoUtils;
import com.qiyuan.base2048.service.common.AttachmentSaver;
import com.qiyuan.base2048.service.common.MinioUploadService;
import com.qiyuan.bautil.dto.MinIoFileDTO;
import com.qiyuan.bautil.dto.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class MinioUploadServiceImpl implements MinioUploadService {
    @Autowired
    private MinIoUtils minIoUtils;
    @Resource
    private AttachmentSaver attachmentSaver;

    @Override
    public ResultDTO saveAttach(MultipartFile multipartFile, String mainGuid,String mainTableName,String mainStyle) throws Exception{
        String originalName = multipartFile.getOriginalFilename();
        String bucketName = minIoUtils.getMinioDefaultBucketName();
        String fileName = minIoUtils.getMinioDefaultFileName(originalName);
        minIoUtils.putObject(bucketName, multipartFile, fileName);
        TbAttachment tbAttachment = attachmentSaver.genTbAttachment(mainGuid,mainStyle,mainTableName,bucketName,fileName,originalName,multipartFile.getContentType(),new BigDecimal(multipartFile.getSize()).toString());
        int rtn = attachmentSaver.saveTbAttachment(tbAttachment);
        if (rtn != 1) {
            return ResultDTO.failureCustom("插入附件信息数据时出错");
        }
        MinIoFileDTO minIoFileDTO = TbAttachmentStructMapper.INSTANCE.toMinIoFileDTO(tbAttachment);
        return ResultDTO.success(minIoFileDTO);
    }
}
