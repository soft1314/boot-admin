package com.qiyuan.base2048.service.oa.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TrNotifyDiv;
import com.qiyuan.base2048.pojo.vo.oa.NotifyAdmDivRelationshipVO;
import com.qiyuan.base2048.service.manage.AdmDivDataGetter;
import com.qiyuan.base2048.service.mybatis.ITrNotifyDivService;
import com.qiyuan.base2048.service.oa.NotifyDivRelationSaver;
import com.qiyuan.bautil.constant.ConstantCommon;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class NotifyDivRelationSaverImpl implements NotifyDivRelationSaver {
    @Resource
    private AdmDivDataGetter admDivDataGetter;
    @Resource
    private ITrNotifyDivService iTrNotifyDivService;

    @Override
    public ResultDTO saveTrNotifyAdmDiv(NotifyAdmDivRelationshipVO notifyAdmDivRelationshipVO) throws Exception {
        /**
         * 对区划编码数组进行精简，有上级编码的，其下级一律丢弃
         * 算法：1.全放在map里，2.遍历编码 3.编码的上级编码未在map中时，放到结果list,否则丢弃 4.遍历结束时得到结果
         */
        List<String> codes = notifyAdmDivRelationshipVO.getAdmDivCodes();
        String notifyGuid = notifyAdmDivRelationshipVO.getNotifyGuid();

        List<String> codesForSave = new ArrayList<>();
        Map map = codes.stream().collect(Collectors.toMap(Function.identity(), s->0));
        for(String code:codes) {
            if(ConstantCommon.ADM_DIV_ROOT_CODE.equals(code)){
                /**
                 * 只有有00，就唯一00
                 */
                codesForSave = new ArrayList<>();
                codesForSave.add(ConstantCommon.ADM_DIV_ROOT_CODE);
                break;
            }else {
                String fatherCode = admDivDataGetter.getFatherCode(code);
                if (!map.containsKey(fatherCode)) {
                    codesForSave.add(code);
                }
            }
        }
        /**
         * 先逻辑删除原记录，再循环插入保存
         */
        this.logicDeleteTrNotifyDivByNotifyGuid(notifyGuid);
        this.batchInsertTrNotifyDiv(notifyGuid,codesForSave);
        return ResultDTO.success();
    }
    private boolean logicDeleteTrNotifyDivByNotifyGuid(String notifyGuid) throws Exception{
        UpdateWrapper<TrNotifyDiv> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("DELETED", IsDeletedEnum.DELETED.getStringValue())
                .eq("NOTIFY_GUID",notifyGuid);
        /** UpdateWrapper 不带entity参数，自动填充无效 **/
        boolean result = iTrNotifyDivService.update(new TrNotifyDiv(),updateWrapper);
        return result;
    }
    private boolean batchInsertTrNotifyDiv(String notifyGuid,List<String> admDivCodes) throws Exception{
        List<TrNotifyDiv> trNotifyDivLstForInsert = new ArrayList<>();
        for(String code:admDivCodes){
            TrNotifyDiv trNotifyDiv = new TrNotifyDiv();
            trNotifyDiv.setDivCode(code);
            trNotifyDiv.setNotifyGuid(notifyGuid);
            trNotifyDiv.setEnabled(IsEnabledEnum.ENABLED.getStringValue());
            trNotifyDiv.setDeleted(IsDeletedEnum.NOTDELETED.getStringValue());
            trNotifyDivLstForInsert.add(trNotifyDiv);
        }

        boolean result = iTrNotifyDivService.saveBatch(trNotifyDivLstForInsert);
        return result;
    }
}
