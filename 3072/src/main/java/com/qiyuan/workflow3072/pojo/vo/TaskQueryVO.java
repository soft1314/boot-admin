package com.qiyuan.workflow3072.pojo.vo;

import com.qiyuan.bautil.dto.BasePageQueryVO;
import lombok.Data;

import java.util.Date;

@Data
public class TaskQueryVO extends BasePageQueryVO {
    private String title;
    private String processDefinitionName;
    private String taskName;
    private Date[] taskCreated;
    private Date[] taskCompleted;
    private Integer processFinish;
}
