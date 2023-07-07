package com.qiyuan.base2048.pojo.vo.query;

import lombok.Data;
import com.qiyuan.bautil.dto.BasePageQueryVO;
/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-11-01 23:02
 **/
@Data
public class ResourcePageQueryParamVO  extends BasePageQueryVO{
    private String resourceName;
    private String uri;
    private String resourceType;
    private String enabled;
    private String roleId;
    private String deleted;
}
