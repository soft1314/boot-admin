package com.qiyuan.base2048.service.generated;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiyuan.base2048.mapper.mybatis.entity.DemoEmp;
import com.qiyuan.base2048.pojo.vo.generated.DemoEmpScene1QueryVO;
import com.qiyuan.base2048.pojo.vo.generated.DemoEmpScene1DataVO;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
/**
* <p>
* 本文件自动生成，严禁人工修改
* 示例表（基本CRUD）人员表 剧情1服务类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2023-3-30 15:10:10
*/
public interface IDemoEmpScene1Service extends IService<DemoEmp> {
  ResultDTO getPage(DemoEmpScene1QueryVO scene1QueryVO, boolean includeDeleted) throws Exception;
  ResultDTO insertOrUpdate(DemoEmpScene1DataVO dataVO, BaseUser baseUser) throws Exception;
  ResultDTO delete(String guid) throws Exception;
}