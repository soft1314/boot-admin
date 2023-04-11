package com.qiyuan.bautil.util;

import org.apache.commons.lang.StringUtils;

public class AdmDivUtil {
    /**
     * 返回串数组，元素依次为本级编码，父级编码，爷级编码......直到最高级区划编码
     * @param mofDivCode
     * @return
     * @throws Exception
     */
    public static String[] getUpMofDivCodeArray(String mofDivCode) throws Exception {
        String[] ret;
        if (StringUtils.isBlank(mofDivCode)) {
            ret = new String[]{""};
            return ret;
        } else if (mofDivCode.length() == 9) {
            ret = new String[]{mofDivCode.substring(0, 2), mofDivCode.substring(0, 4), mofDivCode.substring(0, 6), mofDivCode};
            return ret;
        } else if (mofDivCode.length() == 6) {
            ret = new String[]{mofDivCode.substring(0, 2), mofDivCode.substring(0, 4), mofDivCode};
            return ret;
        } else if (mofDivCode.length() == 4) {
            ret = new String[]{mofDivCode.substring(0, 2), mofDivCode};
            return ret;
        } else if (mofDivCode.length() == 2) {
            ret = new String[]{mofDivCode};
            return ret;
        } else {
            ret = new String[]{""};
            return ret;
        }
    }
}
