package com.qiyuan.base2048.mapper.mapstruct.generated;

import com.qiyuan.base2048.mapper.mybatis.entity.DemoExpense;
import com.qiyuan.base2048.pojo.vo.generated.DemoExpenseScene4DataVO;;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
* <p>
    * 本文件自动生成，严禁人工修改
    * 示例表（业务工作流）报销 Mapstruct 剧情4实体类转换
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2023-3-30 15:16:14
*/
@Mapper
public interface DemoExpenseScene4StructMapper {
  DemoExpenseScene4StructMapper INSTANCE= Mappers.getMapper(DemoExpenseScene4StructMapper.class);
  DemoExpense toDemoExpense(DemoExpenseScene4DataVO vo);
  DemoExpenseScene4DataVO toDemoExpenseScene4DataVO(DemoExpense entity);
}