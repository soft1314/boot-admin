package com.qiyuan.base2048.service.common.impl;

import com.qiyuan.base2048.mapper.mapstruct.TbAttachmentStructMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TbAttachment;
import com.qiyuan.base2048.service.common.AttachmentGetter;
import com.qiyuan.base2048.service.mybatis.ITbAttachmentService;
import com.qiyuan.bautil.dto.MinIoFileDTO;
import com.qiyuan.bautil.dto.ResultDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AttachmentGetterImpl implements AttachmentGetter {
    @Resource
    private ITbAttachmentService iTbAttachmentService;

    @Override
    public ResultDTO fetchAttachmentList(String tbName, String tbStyle, String tbGuid) throws Exception {
        List<TbAttachment> tbAttachmentList = iTbAttachmentService.selectTbAttachmentByMainData(tbName,tbStyle,tbGuid);
        List<MinIoFileDTO> minIoFileDTOList = TbAttachmentStructMapper.INSTANCE.toMinIoFileDTOList(tbAttachmentList);
//        String guids = StringTool.join(",",minIoFileDTOList,"guid");
//        AttachmentDTO attachmentDTO = new AttachmentDTO();
//        attachmentDTO.setAttachFileList(minIoFileDTOList);
//        attachmentDTO.setAttachGuids(guids);
        return ResultDTO.success(minIoFileDTOList);
    }
}
