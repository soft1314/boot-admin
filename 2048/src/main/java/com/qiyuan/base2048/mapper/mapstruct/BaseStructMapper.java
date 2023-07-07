package com.qiyuan.base2048.mapper.mapstruct;

import java.util.Date;

public interface BaseStructMapper {
    default String getUUID(){
        return java.util.UUID.randomUUID().toString();
    }
    default Date getNow(){
        return new Date();
    }
}
