package com.qiyuan.base2048.mapper.mapstruct;

import com.qiyuan.base2048.mapper.mybatis.entity.TbDroolsRule;
import com.qiyuan.base2048.pojo.vo.save.TbDroolsRuleScene1DataVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
 * <p>
 * 本文件自动生成，严禁人工修改
 * 系统规则表 Mapstruct 剧情1实体类转换
 * </p>
 *
 * @author 虚领顶劲气沉丹田
 * @since 2023-4-10
 */
@Mapper
public interface TbDroolsRuleScene1StructMapper {
    TbDroolsRuleScene1StructMapper INSTANCE= Mappers.getMapper(TbDroolsRuleScene1StructMapper.class);
    TbDroolsRule toTbDroolsRule(TbDroolsRuleScene1DataVO vo);
}
