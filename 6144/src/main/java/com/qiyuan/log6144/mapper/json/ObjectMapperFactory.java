package com.qiyuan.log6144.mapper.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ObjectMapperFactory {
    private static ObjectMapper nonStrictObjectMapper = null;
    public static ObjectMapper createNonStrictObjectMapper(){
        if(nonStrictObjectMapper == null) {
            nonStrictObjectMapper = new ObjectMapper();
            // 忽略json字符串中不识别的属性
            nonStrictObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // 忽略无法转换的对象 “No serializer found for class com.xxx.xxx”
            nonStrictObjectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        }

        return nonStrictObjectMapper;
    }
}
