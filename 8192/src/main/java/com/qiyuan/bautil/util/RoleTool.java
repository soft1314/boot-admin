package com.qiyuan.bautil.util;

import java.util.List;

public class RoleTool {
    /**
     * 检查我的角色 myRole 是否在 要求的角色 needRole中
     * @param myRole
     * @param needRole
     * @return
     */
    public static boolean roleCheck(List<String> myRole, List<String> needRole){
        if(myRole == null || needRole == null){
            return false;
        }
        for(String my:myRole){
            for(String need:needRole){
                if(my.equals(need)){
                    return true;
                }
            }
        }
        return false;
    }
}
