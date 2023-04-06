package com.qiyuan.bautil.util;

import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

public class ValidUtil {
    public static String getFormValidErrsMsg(List<ObjectError> objectErrorList) {
        if (objectErrorList==null) {
            return "";
        }
        StringBuffer csv = new StringBuffer();
        csv.append("数据验证未通过：<br>[");
        for (int i = 0; i < objectErrorList.size(); i++)
        {
            if (i > 0)
            {
                csv.append("]<br>[");
            }
            csv.append(objectErrorList.get(i).getDefaultMessage());
        }
        csv.append("]");
        return csv.toString();
    }
    public static String getFormValidErrsMsgNoBr(List<ObjectError> objectErrorList) {
        if (objectErrorList==null) {
            return "";
        }
        StringBuffer csv = new StringBuffer();
        csv.append("数据验证未通过：[");
        for (int i = 0; i < objectErrorList.size(); i++)
        {
            if (i > 0)
            {
                csv.append("],[");
            }
            csv.append(objectErrorList.get(i).getDefaultMessage());
        }
        csv.append("]");
        return csv.toString();
    }

    public static String getValidErrsMsg(Set<ConstraintViolation<Object>> constraintViolationSet){
        if (constraintViolationSet==null) {
            return "";
        }
        StringBuffer csv = new StringBuffer();
        csv.append("数据验证未通过：[");

        int index=0;
        for (ConstraintViolation<Object> model : constraintViolationSet) {
            if(index>0){
                csv.append("][");
            }
            csv.append(model.getMessage());
        }

        csv.append("]");
        return csv.toString();
    }
}
