package com.qiyuan.bautil.service.component;

import com.qiyuan.bautil.enums.YesNoEnum;
import org.springframework.stereotype.Component;

@Component
public class BooleanToCharFormat {
    /**
     * 1表示【是】
     * 0表示【否】
     * @param disabled
     * @return
     */
    public String toString(Boolean disabled){
        if (disabled){
            return YesNoEnum.YES.getValue();
        }
        return YesNoEnum.NO.getValue();
    }

    public Boolean toBoolean(String disable){
        if (YesNoEnum.YES.getValue().equals(disable)){
            return true;
        }
        return false;
    }
}

