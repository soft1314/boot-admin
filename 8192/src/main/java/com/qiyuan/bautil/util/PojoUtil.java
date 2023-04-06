package com.qiyuan.bautil.util;

import org.apache.commons.lang.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PojoUtil {
    /**
     *
     * @author 虚领顶劲气沉丹田
     * @date 2020年7月3日
     * @version 1.0
     *
     * @param dto
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String getDtoInfo(Object dto, Class clazz) {

        String ret = "";

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            String name = field.getName();
            String type = field.getType().toString();
            String value = String.valueOf(getFieldValueByName(field.getName(), dto));

            // System.out.println(field.getName());
            // System.out.println(getFieldValueByName(field.getName(), dto));
            // System.out.println(field.getType().toString());

            // Annotation[] annotations = field.getAnnotations(); // 获取所有的注解
            ApiModelProperty api = field.getDeclaredAnnotation(ApiModelProperty.class); // 获取指定的注解
            if (api != null && StringUtils.isNotEmpty(api.value())) {
                // System.out.println(api.value());
                ret += api.value() + ":" + value + ";";
            }
        }
        return ret;

    }

    /**
     * Dto比较
     *
     * @author 虚领顶劲气沉丹田
     * @date 2020年7月3日
     * @version 1.0
     *
     * @param srcDto
     *            修改前
     * @param targetDto
     *            修改后
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String getDtoCompare(Object srcDto, Object targetDto, Class clazz) {

        String ret = "";
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            String name = field.getName();
            String type = field.getType().toString();
            String srcValue = String.valueOf(getFieldValueByName(field.getName(), srcDto));
            String targetValue = String.valueOf(getFieldValueByName(field.getName(), targetDto));

            ApiModelProperty api = field.getDeclaredAnnotation(ApiModelProperty.class); // 获取指定的注解
            if (api != null && StringUtils.isNotEmpty(api.value())) {
                if (!StringUtils.equals(srcValue, targetValue)) {
                    ret += api.value() + ":" + srcValue + "->" + targetValue + ";";
                }
            }
        }
        return ret;

    }

    /**
     * 根据属性名获取属性值
     *
     * @author 虚领顶劲气沉丹田
     * @date 2020年7月3日
     * @version 1.0
     *
     * @param fieldName
     * @param o
     * @return
     */
    public static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            // log.error(e.getMessage(),e);
            return null;
        }
    }
}
