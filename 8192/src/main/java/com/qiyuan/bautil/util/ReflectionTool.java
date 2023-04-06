package com.qiyuan.bautil.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ReflectionTool {

    /**
     * 根据属性名称从对象中取值
     * @param object
     * @param attributeName
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T getValueFromObject(T object, String attributeName) throws Exception {
        // 得到类对象
        Class userCla = (Class) object.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            f.setAccessible(true); // 设置些属性是可以访问的
            try {
                if (f.getName().equals(attributeName)) {
                    return (T)f.get(object);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 根据属性名称从对象中取值LIST，返回LIST
     * @param objectList
     * @param attributeName
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> getValuesFromObjectList(List<T> objectList, String attributeName) throws Exception {
        List<T> returnList = new ArrayList<>();
        for(T object:objectList){
            returnList.add(ReflectionTool.getValueFromObject(object,attributeName));
        }
        return returnList;
    }

    /**
     * 去除LIST早NULL和重复元素
     * @param objectList
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> removeDuplicationAndNull(List<T> objectList) throws Exception {
        objectList.removeAll(Collections.singleton(null));
        return objectList.stream().distinct().collect(Collectors.toList());
    }

    public static <T> List<T> getNoDuplicationValuesFromObjectList(List<T> objectList, String attributeName) throws Exception {
        List list = ReflectionTool.getValuesFromObjectList(objectList,attributeName);
        return ReflectionTool.removeDuplicationAndNull(list);
    }

}

