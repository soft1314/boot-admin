package com.qiyuan.bautil.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtilsBean;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Slf4j
public class BeanTool {
    /**
     * 判断List中对象某属性的值是否相等
     * @param objectList
     * @param propertyName
     * @return
     * @throws Exception
     */
    public static boolean checkObjectPropertyIsSameValue(List<Object> objectList, String propertyName) throws Exception {
        if (objectList.size() == 1) {
            return true;//只有一个对象，肯定相等
        }
        String firstValue = getValueByKey(objectList.get(0),propertyName);
        for (int i = 1; i < objectList.size(); i++) {
            String currentValue = getValueByKey(objectList.get(i),propertyName);
            if (firstValue.equals(currentValue)) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }
    public static String getValueByKey(Object obj, String key) {
        // 得到类对象
        Class userCla = (Class) obj.getClass();
        /* 得到本类以及父类直到非neusoft类中的所有属性集合*/
        Field[] fs = BeanTool.getAllNeusoftField(userCla);

        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            f.setAccessible(true); // 设置些属性是可以访问的
            try {
                if (f.getName().equals(key)) {
                    if(f.get(obj) == null){
                        return "";
                    }else {
                        return f.get(obj).toString();
                    }
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        // 没有查到时返回空字符串
        return "";
    }
    /**
     * 根据属性名称设置串值
     * @param dstob Object
     * @param propertyname String
     * @param values String
     * @return boolean
     */
    public static boolean setProperty(Object dstob,String propertyname,String values) {
        String datatype="";
        if (dstob == null) {
            return false;
        }else {
            BeanUtilsBean bean = BeanUtilsBean.getInstance();
            try {
                datatype=getPropertyType(dstob,propertyname.toLowerCase());
                if ("java.util.Date".equals(datatype)){
                    bean.setProperty(dstob, propertyname.toLowerCase(),
                            TimeTool.stringToDate(values,"yyyy-MM-dd"));
                }else{
                    bean.setProperty(dstob, propertyname.toLowerCase(),
                            values);
                }
            }
            catch (Exception ex) {
                log.error("Exception on setPropertys,src property name:"+propertyname);
            }
        }
        return true;
    }
    /**
     * 根据属性名称，取一个未知类的指定属性的数据类型
     * @param obj Object
     * @param propertyName String
     * @return String
     */
    public static String getPropertyType(Object obj,String propertyName){
        String ret="";
        try {
            Class cls = obj.getClass();
            Field fd = cls.getDeclaredField(propertyName);
            Class t = fd.getType();
            ret=t.getName();
        } catch (SecurityException ex) {
        } catch (NoSuchFieldException ex) {
        }
        return ret;
    }
    public static boolean setProperty(Object dstob,String propertyname,Object values) {
        String datatype="";
        if (dstob == null) {
            return false;
        }else {
            BeanUtilsBean bean = BeanUtilsBean.getInstance();
            try {
                bean.setProperty(dstob, propertyname.toLowerCase(),values);
            }
            catch (Exception ex) {
                log.error("Exception on setPropertys,src property name:"+propertyname);
            }
        }
        return true;
    }
    /**
     * 从对象之中取串值
     * java 8 不支持了
     * @param obj Object
     * @param propertyName String
     * @return String
     */
//    public static String getPropertyStr(Object obj,String propertyName){
//        BeanUtilsBean bean = BeanUtilsBean.getInstance();
//        String ret = null;
//        try {
//            ret = (String) bean.getProperty(obj, propertyName);
//        } catch (Exception ex) {
//            ret="";
//        }
//        return ret;
//    }
//    public static String getPropertyStr(Object obj,String propertyName) throws Exception{
//        Class c = obj.getClass();
//        // 获取该类的成员变量
//        Field f = c.getDeclaredField(propertyName);
//        // 取消语言访问检查
//        f.setAccessible(true);
//        // 给变量取值
//        if(null == f.get(obj)){
//            return "";
//        }
//        String ret = f.get(obj).toString();
//        return ret;
//    }
    public static String getPropertyStr(Object obj,String propertyName) throws Exception{
        Object val = BeanTool.getFieldValueByName(propertyName,obj);
        if(val == null){
            return "";
        }
        return val.toString();
    }
    public static Date getPropertyDate(Object obj, String propertyName) throws Exception{
        Object val = BeanTool.getFieldValueByName(propertyName,obj);
        if(val == null){
            return null;
        }
        return (Date)val;
    }
    /**
     * 从对象之中取BigDecimal值
     * @param obj Object
     * @param propertyName String
     * @return String
     */
    public static BigDecimal getPropertyBigDecimal(Object obj, String propertyName){
        Object val = BeanTool.getFieldValueByName(propertyName,obj);
        return (BigDecimal)val;
    }
    public static int getPropertyInt(Object obj, String propertyName){
        Object val = BeanTool.getFieldValueByName(propertyName,obj);
        if(val == null){
            return 0;
        }
        return ((Integer) val).intValue();
    }
    public static Double getPropertyDouble(Object obj, String propertyName){
        Object val = BeanTool.getFieldValueByName(propertyName,obj);
        return (Double)val;
    }
    public static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * 递归获取类，父类的类中的所有属性集合
     * 只处理包含neusoft的类
     * @return
     */
    public static Field[] getAllNeusoftField(Class cls){
        /* 得到类中的所有属性集合*/
        Field[] allField = cls.getDeclaredFields();
        if(cls.getSuperclass().getName().contains("neusoft")){
            return allField = ArrayTool.concatAll(allField,getAllNeusoftField(cls.getSuperclass()));
        }
        return allField;
    }


    /**
     * 求和
     * @param objList
     * @param propertyName
     * @return
     */
    public static <T> BigDecimal sumPropertyBigDecimal(List<T> objList, String propertyName){
        if(objList == null || objList.size() == 0){
            return BigDecimal.ZERO;
        }
        BigDecimal sum = getPropertyBigDecimal(objList.get(0),propertyName);
        for(int i=1;i<objList.size();i++){
            sum = sum.add(getPropertyBigDecimal(objList.get(i),propertyName));
        }
        return sum;
    }

    public static <T> String[] getStringPropertyArray(List<T> objList,String propertyName) throws Exception{
        if(objList == null || StringUtil.isBlank(propertyName)){
            return new String[0];
        }

        String[] values = new String[objList.size()];
        for(int i=0;i<objList.size();i++){
            values[i] = BeanTool.getPropertyStr(objList.get(i),propertyName);
        }
        return values;
    }
    public static <T> BigDecimal[] getBigDecimalPropertyArray(List<T> objList,String propertyName) throws Exception{
        if(objList == null || StringUtil.isBlank(propertyName)){
            return new BigDecimal[0];
        }

        BigDecimal[] values = new BigDecimal[objList.size()];
        for(int i=0;i<objList.size();i++){
            values[i] = BeanTool.getPropertyBigDecimal(objList.get(i),propertyName);
        }
        return values;
    }

    public static <T> boolean checkBeanPropertyIsSameValue(List<T> objectList, String propertyName) throws Exception {
        if (objectList.size() == 1) {
            return true;//只有一个对象，肯定相等
        }
        String firstValue = getValueByKey(objectList.get(0),propertyName);
        for (int i = 1; i < objectList.size(); i++) {
            String currentValue = getValueByKey(objectList.get(i),propertyName);
            if (firstValue.equals(currentValue)) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }


    /**
     * 父类向子类转换，融合数据
     * @param result    子类对象
     * @param parent    父类对象
     * @param <T>   转换结果
     * @return
     * @throws IllegalAccessException
     */
    public static <T> T pToC(T result,Object parent) throws IllegalAccessException{
        if(parent.getClass().isAssignableFrom(result.getClass())){
            Class parentClass=parent.getClass();
            Field[] parentFields=parentClass.getDeclaredFields();
            for(Field parentField:parentFields){
                if(!Modifier.isFinal(parentField.getModifiers())){
                    parentField.setAccessible(true);
                    Object value=parentField.get(parent);
                    parentField.set(result,value);
                }
            }
        }
        return result;
    }

    /**
     * 对象属性转换为字段  例如：userName to user_name
     * @param property 字段名
     * @return
     */
    public static String propertyToField(String property) {
        if (null == property) {
            return "";
        }
        char[] chars = property.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (char c : chars) {
            if (CharUtil.isAsciiAlphaUpper(c)) {
                sb.append("_" + StringUtil.lowerCase(CharUtil.toString(c)));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 字段转换成对象属性 例如：user_name to userName
     * @param field
     * @return
     */
    public static String fieldToProperty(String field) {
        if (null == field) {
            return "";
        }
        char[] chars = field.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '_') {
                int j = i + 1;
                if (j < chars.length) {
                    sb.append(StringUtil.upperCase(CharUtil.toString(chars[j])));
                    i++;
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}

