package com.qiyuan.bautil.service.component;

import org.springframework.stereotype.Component;

@Component
public class BooleanToCharFormat {
    /**
     * 1表示可修改
     * 0表示不可修改
     * @param disabled
     * @return
     */
    public String toString(Boolean disabled){
        if (disabled){
            return "1";
        }
        return "0";
    }

    public Boolean toBoolean(String disable){
        if (disable.equals("1")){
            return true;
        }
        return false;
    }
}

