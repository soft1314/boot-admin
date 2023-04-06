package com.qiyuan.bautil.util;

import java.util.Arrays;

public class ArrayTool {
    /**
     * 数组合并
     * @param first
     * @param rest
     * @param <T>
     * @return
     */
    public static <T> T[] concatAll(T[] first, T[]... rest) {
        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (T[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    /**
     * 一个数组中的元素是否在另一个数组中
     * @param arr1
     * @param arr2
     * @return
     */
    public static boolean arrayItemInclude(String[] arr1,String[] arr2){
        for(String item1:arr1){
            for(String item2:arr2){
                if(item1.equals(item2)){
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 返回元素在数组中的索引值
     * @param array
     * @param item
     * @return
     */
    public static int getIndex(int[] array, int item) {
        int index=-1;
        for(int i=0;i<array.length;i++){
            if(item==array[i]){
                index=i;
            }
        }
        return index;
    }
    /**
     * 返回元素在数组中的索引值
     * @param array
     * @param item
     * @return
     */
    public static int getIndex(String[] array, String item) {
        int index=-1;
        for(int i=0;i<array.length;i++){
            if(item.equals(array[i])){
                index=i;
            }
        }
        return index;
    }
}

