package com.qiyuan.bautil.util;

public class OracleTool {
    public static String dataTypeToJavaType(String dataType){
        if("NVARCHAR2".equalsIgnoreCase(dataType)){
            return "String";
        }else if("VARCHAR2".equalsIgnoreCase(dataType)){
            return "String";
        }else if("NUMBER".equalsIgnoreCase(dataType)) {
            return "BigDecimal";
        }else if("CLOB".equalsIgnoreCase(dataType)) {
            return "String";
        }else if("DATE".equalsIgnoreCase(dataType)) {
            return "Date";
        }else if("TIMESTAMP(3)".equalsIgnoreCase(dataType)) {
            return "Date";
        }else if("TIMESTAMP(6)".equalsIgnoreCase(dataType)) {
            return "Date";
        }else if("BLOB".equalsIgnoreCase(dataType)) {
            return "Object";
        }
        return "String";
    }
}
