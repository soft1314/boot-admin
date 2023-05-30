package com.qiyuan.bautil.util;

public class UrlTool {
    /**
     * /api/XXX/free/XX 结构的为真
     * @param url
     * @return
     * @throws Exception
     */
    public static boolean isFreeUrl(String url){
        if(StringUtil.isBlank(url)){
            return false;
        }
        String[] urlArray = url.split("/");
        if(urlArray.length<4){
            return false;
        }
        if("api".equalsIgnoreCase(urlArray[1]) && "free".equalsIgnoreCase(urlArray[3])){
            return true;
        }
        return false;
    }
}
