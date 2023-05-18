package com.qiyuan.base2048.service.mybatis;

import com.qiyuan.base2048.mapper.mybatis.entity.TbAttachment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* <p>
* 本文件自动生成，严禁人工修改
* 系统附件数据表 服务类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2023-5-9 14:25:25
*/
public interface ITbAttachmentService extends IService<TbAttachment> {
    List<TbAttachment> selectTbAttachmentByMainData(String tbName, String tbStyle, String tbGuid) throws Exception;
    Boolean deleteAttachment(String guid,String fileName) throws Exception;
}