package com.qiyuan.bautil.util;

import java.util.Map;

public class MapTool {
    public static String getStringValue(Map map, String key) throws Exception{
        Object var = map.get(key);
        if(var != null){
            return var.toString();
        }
        return "";
    }

}
