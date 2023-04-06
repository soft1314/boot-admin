package com.qiyuan.bautil.util;

import com.qiyuan.bautil.dto.BaseUser;
import org.apache.commons.lang.StringUtils;

public class WorkflowTool {
    /**
     * 拼接Assignee串
     * @param baseUser
     * @return
     */
    public static String genAssignee(BaseUser baseUser){
        return baseUser.getUserName()+"~"+baseUser.getLogonName()+"~"+baseUser.getUserGuid();
    }
    public static String getAssigneeUserName(String assignee){
        if(StringUtils.isBlank(assignee)){
            return "";
        }
        String[] arr = assignee.split("~");
        if(arr.length>0){
            return arr[0];
        }
        return "";
    }

    public static String getAssigneeLogonName(String assignee){
        if(StringUtils.isBlank(assignee)){
            return "";
        }
        String[] arr = assignee.split("~");
        if(arr.length>1){
            return arr[1];
        }
        return "";
    }

    public static String getAssigneeUserGuid(String assignee){
        if(StringUtils.isBlank(assignee)){
            return "";
        }
        String[] arr = assignee.split("~");
        if(arr.length>2){
            return arr[2];
        }
        return "";
    }

    /**
     * Assignee串中拼接成友好显示的串
     * @param realAssignee
     * @return
     */
    public static String getHumanAssignee(String realAssignee){
        String humanAssignee;
        String userName= WorkflowTool.getAssigneeUserName(realAssignee);
        String logonName=WorkflowTool.getAssigneeLogonName(realAssignee);
        String userGuid=WorkflowTool.getAssigneeUserGuid(realAssignee);

        if (StringUtils.isNotBlank(userName)) {
            humanAssignee = userName;
            if (StringUtils.isNotBlank(logonName)) {
                humanAssignee = humanAssignee+"【"+logonName+"】";
            }
        }else{
            if (StringUtils.isNotBlank(logonName)) {
                humanAssignee = logonName+"【"+userGuid+"】";
            }else{
                humanAssignee = userGuid;
            }
        }
        return humanAssignee;
    }
    /**
     * 获取指定子串之前的部分
     * 如从 “通过【****】”取出“通过”，否则返回全部串
     * @param str
     * @param span
     * @return
     * @throws Exception
     */
    public static String getPrefixStr(String str,String span) throws Exception{
        int pos = str.indexOf(span);
        if(pos > 0) {
            return str.substring(0, pos);
        }else{
            return str;
        }
    }

    /**
     * 拼接任务批注
     * @param result
     * @param note
     * @return
     */
    public static String genComment(String result,String note){
        if(StringUtils.isBlank(note)){
            return result;
        }
        return result+"【"+note+"】";
    }
}
