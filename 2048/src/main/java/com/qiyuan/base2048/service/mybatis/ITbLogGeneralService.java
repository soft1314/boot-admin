package com.qiyuan.base2048.service.mybatis;

import com.qiyuan.base2048.mapper.mybatis.entity.TbLogGeneral;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* <p>
* 本文件自动生成，严禁人工修改
* 一般日志表 服务类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2023-3-14 11:20:46
*/
public interface ITbLogGeneralService extends IService<TbLogGeneral> {
    boolean createLog(String logContent) throws Exception;

}