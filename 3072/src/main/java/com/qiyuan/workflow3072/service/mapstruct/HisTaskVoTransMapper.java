package com.qiyuan.workflow3072.service.mapstruct;

import com.qiyuan.workflow3072.pojo.vo.HisTaskVO;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface HisTaskVoTransMapper {
    HisTaskVoTransMapper INSTANCE = (HisTaskVoTransMapper) Mappers.getMapper(HisTaskVoTransMapper.class);

    HisTaskVO historicTaskInstanceToHisTaskVo(HistoricTaskInstance historicTaskInstance);
    List<HisTaskVO> historicTaskInstanceToHisTaskVoList(List<HistoricTaskInstance> historicTaskInstances);
}
