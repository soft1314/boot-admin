package com.qiyuan.base2048.mapper.mapstruct.generated;

import com.qiyuan.base2048.mapper.mybatis.entity.DemoLeave;
import com.qiyuan.base2048.pojo.vo.generated.DemoLeaveScene3StartProcessDataVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
* <p>
    * 本文件自动生成，严禁人工修改
    * 示例表（基本工作流）请假 Mapstruct 剧情1实体类转换
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2023-3-30 15:13:26
*/
@Mapper
public interface DemoLeaveScene3StructMapper {
    DemoLeaveScene3StructMapper INSTANCE= Mappers.getMapper(DemoLeaveScene3StructMapper.class);
    DemoLeave toDemoLeave(DemoLeaveScene3StartProcessDataVO vo);
    DemoLeaveScene3StartProcessDataVO toDemoLeaveScene3StartProcessDataVO(DemoLeave entity);
}