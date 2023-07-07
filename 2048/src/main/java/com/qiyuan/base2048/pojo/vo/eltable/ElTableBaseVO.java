package com.qiyuan.base2048.pojo.vo.eltable;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 用于绑定El-Table组件的数据行对象的基类
 * GUID：
 *  如果从后台取过来的数据，保持住，用于传回后台修改数据的主键
 *  如果从前端单独生成的GUID，用于后台插入时防止重复提交（不重复时插入，重复时修改）
 * VERSION：
 *  控制修改数据,如果从前台生成，初值为0
 *  如果从后台取过来的数据，则修改时乐观锁机制生效，控制脏写
 */
@Data
public class ElTableBaseVO {
    /**
     * 主键GUID
     */
    private String guid;
    /**
     *  乐观锁
     */
    private BigDecimal version;
}
