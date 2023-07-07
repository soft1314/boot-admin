package com.qiyuan.base2048.service.oa.impl;

import com.qiyuan.base2048.mapper.mybatis.entity.*;
import com.qiyuan.base2048.service.mybatis.*;
import com.qiyuan.base2048.service.oa.NotifySender;
import com.qiyuan.bautil.constant.ConstantCommon;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import com.qiyuan.bautil.enums.YesNoEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NotifySenderImpl implements NotifySender {
    @Resource
    private IToNotifyRecordService iToNotifyRecordService;
    @Resource
    private ITrNotifyDivService iTrNotifyDivService;
    @Resource
    private ITrNotifyOrgService iTrNotifyOrgService;
    @Resource
    private ITbUserService iTbUserService;
    @Resource
    private IToNotifyService iToNotifyService;
    @Resource
    private IToContentService iToContentService;
    @Resource
    private ITbAttachmentService iTbAttachmentService;

    @Override
    public ResultDTO preSend(String notifyGuid) throws Exception {
        ToContent toContent = iToContentService.getById(notifyGuid);
        if(toContent == null){
            return ResultDTO.failureCustom("无正文。");
        }
        if(StringUtils.isBlank(toContent.getContent())){
            return ResultDTO.failureCustom("正文空。");
        }

        List<TbAttachment> tbAttachments = iTbAttachmentService.selectTbAttachmentByMainData("tb_notify","common",notifyGuid);
        int contentSize = toContent.getContent().length();
        int attachNum = tbAttachments.size();
        if(attachNum == 0){
            return ResultDTO.successCustom("正文字符个数：【"+contentSize+"】个，未上传附件。");
        }
        return ResultDTO.successCustom("正文字符个数：【"+contentSize+"】个，上传附件个数：【"+attachNum+"】个。");
    }

    @Override
    public ResultDTO send(String notifyGuid) throws Exception {
        Map receiverMap = this.loadReceiverMap(notifyGuid);
        List<ToNotifyRecord> toNotifyRecords = new ArrayList<>();
        /**
         * 生成通知列表
         * 依次按区划，机构，人员
         */
        this.sendByDiv(notifyGuid, receiverMap, toNotifyRecords);
        this.sendByOrg(notifyGuid, receiverMap, toNotifyRecords);
        this.sendByEmp(notifyGuid, receiverMap, toNotifyRecords);
        if(toNotifyRecords.size() == 0){
            return ResultDTO.successCustom("没有符合通知条件的用户！");
        }
        /**
         * 保存入库
         */
        if(iToNotifyRecordService.saveBatch(toNotifyRecords)) {
            iToNotifyService.changeToPublished(notifyGuid);
            return ResultDTO.successCustom("本次成功通知到【" + toNotifyRecords.size() + "】个用户。");
        }else{
            return ResultDTO.failureCustom("发送通知通告时失败。");
        }
    }
    /**
     * 获取已发送MAP
     * @param notifyGuid
     * @return
     * @throws Exception
     */
    private Map loadReceiverMap(String notifyGuid) throws Exception {
        List<ToNotifyRecord> toNotifyRecordList = iToNotifyRecordService.selectByNotifyGuid(notifyGuid);
        Map<String, String> receiverMap = toNotifyRecordList.stream().collect(Collectors.toMap(ToNotifyRecord::getUserGuid, ToNotifyRecord::getGuid));
        return receiverMap;
    }
    /**
     * 按区划发送
     * @param notifyGuid
     * @param receiverMap
     * @param toNotifyRecords
     * @throws Exception
     */
    private void sendByDiv(String notifyGuid, Map receiverMap, List<ToNotifyRecord> toNotifyRecords) throws Exception {
        List<TrNotifyDiv> trNotifyDivList = iTrNotifyDivService.selectByNotifyGuid(notifyGuid);
        for (TrNotifyDiv trNotifyDiv : trNotifyDivList) {
            /**
             * 00 区划，指全部人员，就不用再循环了
             */
            if(ConstantCommon.ADM_DIV_ROOT_CODE.equals(trNotifyDiv.getDivCode())){
                this.sendAllUser(notifyGuid,receiverMap,toNotifyRecords);
                break;
            }
            this.sendOneDiv(notifyGuid,trNotifyDiv.getDivCode(), receiverMap, toNotifyRecords);
        }
    }
    /**
     * 按机构发送
     * @param notifyGuid
     * @param receiverMap
     * @param toNotifyRecords
     * @throws Exception
     */
    private void sendByOrg(String notifyGuid, Map receiverMap, List<ToNotifyRecord> toNotifyRecords) throws Exception {
        List<TrNotifyOrg> trNotifyOrgList = iTrNotifyOrgService.getTrNotifyOrgList(notifyGuid);
        for (TrNotifyOrg trNotifyOrg : trNotifyOrgList) {
            this.sendOneOrg(notifyGuid,trNotifyOrg.getOrgGuid(), receiverMap, toNotifyRecords);
        }
    }
    /**
     * 按人员发送
     * @param notifyGuid
     * @param receiverMap
     * @param toNotifyRecords
     * @throws Exception
     */
    private void sendByEmp(String notifyGuid, Map receiverMap, List<ToNotifyRecord> toNotifyRecords) throws Exception {
        List<TbUser> tbUserList = iTbUserService.selectTbUserByNotifyGuidWithEmpRelation(notifyGuid);
        this.prepareSendList(notifyGuid,tbUserList,receiverMap,toNotifyRecords);
    }


    /**
     * 通知到一个区划下的用户
     * @param admDivCode
     * @param receiverMap
     * @param toNotifyRecords
     * @throws Exception
     */
    private void sendOneDiv(String notifyGuid,String admDivCode, Map receiverMap, List<ToNotifyRecord> toNotifyRecords) throws Exception {
        List<TbUser> tbUserList = iTbUserService.selectTbUserByDiv(admDivCode);
        this.prepareSendList(notifyGuid,tbUserList,receiverMap,toNotifyRecords);
    }
    private void sendOneOrg(String notifyGuid,String orgGuid, Map receiverMap, List<ToNotifyRecord> toNotifyRecords) throws Exception {
        List<TbUser> tbUserList = iTbUserService.selectTbUserByOrg(orgGuid);
        this.prepareSendList(notifyGuid,tbUserList,receiverMap,toNotifyRecords);
    }
    /**
     * 通知全部用户
     * @param notifyGuid
     * @param receiverMap
     * @param toNotifyRecords
     * @throws Exception
     */
    private void sendAllUser(String notifyGuid, Map receiverMap, List<ToNotifyRecord> toNotifyRecords) throws Exception {
        List<TbUser> tbUserList = iTbUserService.selectAllTbUser();
        this.prepareSendList(notifyGuid,tbUserList,receiverMap,toNotifyRecords);
    }
    /**
     * 生产待保存实体类
     * @param notifyGuid
     * @param userGuid
     * @return
     * @throws Exception
     */
    private ToNotifyRecord genToNotifyRecord(String notifyGuid,String userGuid) throws Exception{
        ToNotifyRecord toNotifyRecord = new ToNotifyRecord();
        toNotifyRecord.setNotifyGuid(notifyGuid);
        toNotifyRecord.setUserGuid(userGuid);
        toNotifyRecord.setEnabled(IsEnabledEnum.ENABLED.getStringValue());
        toNotifyRecord.setDeleted(IsDeletedEnum.NOTDELETED.getStringValue());
        toNotifyRecord.setHaveRead(YesNoEnum.NO.getValue());
        return toNotifyRecord;
    }
    /**
     * 判断是否已发，未发则发送，已发则略过
     * @param notifyGuid
     * @param tbUsers
     * @param receiverMap
     * @param toNotifyRecords
     * @throws Exception
     */
    private void prepareSendList(String notifyGuid,List<TbUser> tbUsers,Map receiverMap,List<ToNotifyRecord> toNotifyRecords) throws Exception{
        for(TbUser tbUser:tbUsers){
            if(!receiverMap.containsKey(tbUser.getGuid())){
                ToNotifyRecord toNotifyRecord = this.genToNotifyRecord(notifyGuid,tbUser.getGuid());
                toNotifyRecords.add(toNotifyRecord);
                receiverMap.put(tbUser.getGuid(),"1");
            }
        }
    }
}
