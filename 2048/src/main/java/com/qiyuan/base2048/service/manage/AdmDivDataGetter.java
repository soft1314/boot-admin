package com.qiyuan.base2048.service.manage;

import com.qiyuan.base2048.mapper.mybatis.entity.TbDictype;
import com.qiyuan.base2048.mapper.mybatis.entity.TrNotifyDiv;
import com.qiyuan.base2048.pojo.vo.query.DictypeQueryVO;
import com.qiyuan.base2048.pojo.vo.query.TbDictionaryQueryVO;
import com.qiyuan.bautil.dto.ResultDTO;

import java.util.List;

public interface AdmDivDataGetter {
    //取下一层级区划
    ResultDTO getOneLevelAdmDivNode(String guid) throws Exception;
//    ResultDTO getInitCheckedTree(String notifyGuid) throws Exception;

    /**
     * 获取通知通告行政区划树缺省勾选的分支
     * @param notifyGuid
     * @return
     * @throws Exception
     */
    ResultDTO getNotifyDefaultCheckedKeys(String notifyGuid) throws Exception;
    /**
     * 获取通知通告行政区划树缺省展开的分支
     * @param notifyGuid
     * @return
     * @throws Exception
     */
    ResultDTO getNotifyDefaultExpandedKeys(String notifyGuid) throws Exception;

    /**
     * 取区划编码的上级编码
     * @param admDivCode
     * @return
     */
    String getFatherCode(String admDivCode) throws Exception;
}
