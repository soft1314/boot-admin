package com.qiyuan.base2048.mapper.mapstruct;

import com.qiyuan.base2048.mapper.mybatis.entity.TbLogGeneral;
import com.qiyuan.base2048.pojo.vo.save.TbLogGeneralScene1DataVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
 * <p>
 * 本文件自动生成，严禁人工修改
 * 一般日志表 Mapstruct 剧情1实体类转换
 * </p>
 *
 * @author 虚领顶劲气沉丹田
 * @since 2023-4-11 14:49:49
 */
@Mapper
public interface TbLogGeneralScene1StructMapper {
    TbLogGeneralScene1StructMapper INSTANCE= Mappers.getMapper(TbLogGeneralScene1StructMapper.class);
    TbLogGeneral toTbLogGeneral(TbLogGeneralScene1DataVO vo);
}
