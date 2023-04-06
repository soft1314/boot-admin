package com.qiyuan.bautil.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTool {
    private static final Logger logger = LoggerFactory.getLogger(StringTool.class);


    //为数据库的保存使用，防止数据库字段超长问题发生
    public static String subStringDb(String str, int size) {
        if (size == 0){
            return str;
        }
        String rtn = (str == null) ? "" : str;
        try {
            byte[] tmpbyte = rtn.getBytes("UTF-8");
            while (tmpbyte.length > size) {
                rtn = rtn.substring(0, rtn.length() - 1);
                tmpbyte = rtn.getBytes("UTF-8");
            }
        } catch (UnsupportedEncodingException ex) {
        }
        return rtn;
    }
    /**下划线转驼峰*/
    private static Pattern linePattern = Pattern.compile("_(\\w)");
    public static String lineToHump(String str){
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    public static String lineToHumpUpperCase(String str){
        String ret = lineToHump(str);
        return toUpperCaseFirstOne(ret);
    }
    private static Pattern humpPattern = Pattern.compile("[A-Z]");
    /**驼峰转下划线,效率比上面高*/
    public static String humpToLine(String str){
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, "_"+matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**驼峰转中划线 - */
    public static String humpToLineMinusSign(String str){
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, "-"+matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    /**
     * 前面补0至指定的长度
     * @param toLen
     * @param code
     * @return
     */
    public static String getCodeByLength(int toLen,String code){
        if(toLen<=code.length()){
            return code;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for(int p=0;p<toLen-code.length();p++){
            stringBuffer.append("0");
        }
        return stringBuffer.append(code).toString();
    }

    /**
     * 取第一个角色
     * @param flowContent
     * @return
     * @throws Exception
     */
    public static String getFirstRoleFromFLowContent(String flowContent) throws Exception{
        if(StringUtils.isBlank(flowContent)){
            return "";
        }
        String[] roleArray = flowContent.split(",");
        return roleArray[0];
    }

    /**
     * 判断字符串是否是数字
     * @param str
     * @return
     */

    private static final Pattern PATTERN_IS_NUMERICZIDAI = Pattern.compile("-?[0-9]+\\.?[0-9]*");
    public static boolean isNumericzidai(String str) {
        Matcher isNum = PATTERN_IS_NUMERICZIDAI.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
    /**
     * 判断字符串是否是整数
     * @param bot
     * @return
     */
    public static boolean isMatches(String bot){
        if(StringUtils.isBlank(bot)){
            return false;
        }
        boolean flag=false;
        try{
            String regex="^[1-9]+[0-9]*$";
            Pattern p=Pattern.compile(regex);
            Matcher m=p.matcher(bot);
            if(m.find()){
                flag = true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 把数组中各ID的_1,_2去掉
     * @param planIdArray
     * @return
     * @throws Exception
     */
    public static String[] getRealPlanIdArray(String[] planIdArray) throws Exception{
        String[] ret = new String[planIdArray.length];
        for(int i=0;i<planIdArray.length;i++){
            if(planIdArray[i].contains("_1")||planIdArray[i].contains("_2")){
                ret[i] = planIdArray[i].substring(0,planIdArray[i].length()-2);
            }
        }
        return ret;
    }
    /**
     * 把数组中各ID的_1,_2去掉
     * @param planIds
     * @return
     * @throws Exception
     */
    public static String[] getRealPlanIdArray(String planIds) throws Exception{
        String[] planIdsArray=planIds.split(",");
        return StringTool.getRealPlanIdArray(planIdsArray);
    }

    /**
     * 删除开头字符串
     **/
    public static String trimStart(String inStr, String prefix) {
        if (inStr.startsWith(prefix)) {
            return (inStr.substring(prefix.length()));
        }
        return inStr;
    }
    public static String trimStartLoop(String inStr, String prefix) {
        String resultString = inStr;
        while (true){
            String currentString = trimStart(resultString,prefix);
            if(resultString.equals(currentString)){
                return resultString;
            }
            resultString = currentString;
        }
    }

    /**
     * 删除末尾字符串
     **/
    public static String trimEnd(String inStr, String suffix) {
        if (inStr.endsWith(suffix)) {
            return (inStr.substring(0, inStr.length() - suffix.length()));
        }
        return inStr;
    }
    public static String trimEndLoop(String inStr, String prefix) {
        String resultString = inStr;
        while (true){
            String currentString = trimEnd(resultString,prefix);
            if(resultString.equals(currentString)){
                return resultString;
            }
            resultString = currentString;
        }
    }
    public static int toInt(String str)
    {
        int irtn=-1;
        if(str==null|| "".equals(str)) {
            return irtn;
        }
        try {
            irtn=Integer.parseInt(str);
        } catch( NumberFormatException  nume ) {
            System.out.println("toInt:" + nume.getMessage());
        }
        return irtn;
    }

    /**
     * 循环替换子串，一直到找不到
     * @param srcStr
     * @param oldStr
     * @param newStr
     * @return
     * @throws Exception
     */
    public static String loopReplace(String srcStr,String oldStr,String newStr) throws Exception{
        String lastStr = srcStr;
        String currentStr = lastStr.replace(oldStr,newStr);
        while (currentStr.length() != lastStr.length()){
            lastStr = currentStr;
            currentStr = lastStr.replace(oldStr,newStr);
        }
        return currentStr;
    }

    public static String addString(String src1,String src2,int maxLen) throws Exception{
        String dst = null;
        if(src1 == null && src2 == null){
            dst = "";
        }else{
            if(src1 == null){
                dst = src2;
            }else if(src2 == null){
                dst = src1;
            }else{
                dst = src1+src2;
            }
        }
        return StringTool.subStringDb(dst,maxLen);
    }

    public static int getStringDbLength(String str) throws Exception{
        byte[] tmpbyte = str.getBytes("UTF-8");
        return tmpbyte.length;
    }

    /**
     * GUID 瘦身
     *
     * @param guid
     * @return
     * @throws Exception
     */
    public static String cutGuid(String guid) throws Exception{
        if(StringUtils.isBlank(guid)){
            return "";
        }
        String ret = guid.replace("-","");
        if(StringTool.getStringDbLength(ret) > 32){
            return subStringDb(ret,32);
        }else{
            return ret;
        }
    }
    /**
     * 使用指定分隔符连接数组各元素，生成串
     * Joins the elements of the provided array into a single string
     * containing a list of CSV elements.
     *
     * @param list      The list of values to join together.
     * @param separator The separator character.
     * @return          The CSV text.
     */
    public static String join(String separator, String[] list)
    {
        if (list==null) {
            list = new String[0];
        }
        StringBuffer csv = new StringBuffer();
        for (int i = 0; i < list.length; i++)
        {
            if (i > 0)
            {
                csv.append(separator);
            }
            csv.append(list[i]);
        }
        return csv.toString();
    }

    /**
     * 使用指定分隔符连接集合各元素，生成串
     * @param separator
     * @param list
     * @return
     */
    public static String join(String separator, Collection<String> list)
    {
        if(list == null){
            return "";
        }
        StringBuffer csv = new StringBuffer();
        Iterator it = list.iterator();
        for (int i = 0; it.hasNext(); i++)
        {
            if (i > 0)
            {
                csv.append(separator);
            }
            csv.append((String)it.next());
        }
        return csv.toString();
    }

    /**
     * 使用指定分隔符连接List中所有对象的指定属性值连成串
     * @param separator
     * @param list
     * @param propertyName
     * @return
     * @throws Exception
     */
    public static String join(String separator, List list, String propertyName) throws Exception{
        if (list==null) {
            list = new ArrayList();
        }
        StringBuffer csv = new StringBuffer();
        int index = 0;
        for (int i = 0; i < list.size(); i++){
            if(BeanTool.getValueByKey(list.get(i),propertyName) == null) {
                continue;
            }
            if (index > 0){
                csv.append(separator);
            }
            index++;
            csv.append(BeanTool.getValueByKey(list.get(i), propertyName));
        }
        return csv.toString();
    }
    public static String join(String separator, Object[] list, String propertyName) throws Exception{
        if (list==null) {
            return "";
        }
        StringBuffer csv = new StringBuffer();
        int index = 0;
        for (int i = 0; i < list.length; i++){
            if(BeanTool.getValueByKey(list[i],propertyName) == null){
                continue;
            }
            if (index > 0){
                csv.append(separator);
            }
            index++;
            csv.append(BeanTool.getValueByKey(list[i],propertyName));
        }
        return csv.toString();
    }
    /**
     * 取指定值连成串
     * @param separator
     * @param list
     * @param propertyName
     * @return
     * @throws Exception
     */
    public static String joinMap(String separator, List<Map> list, String propertyName) throws Exception{
        if (list==null) {
            list = new ArrayList();
        }
        StringBuffer csv = new StringBuffer();
        for (int i = 0; i < list.size(); i++)
        {
            if (i > 0)
            {
                csv.append(separator);
            }
            csv.append(list.get(i).get(propertyName).toString());
        }
        return csv.toString();
    }
    /**
     * 将double格式化为小数点后2位
     * @param d
     * @return
     */
    public static String formatDouble(double d)
    {
        NumberFormat formatter = new DecimalFormat("0.00");
        return formatter.format(d);
    }
    /**
     * 将double格式化为小数点后指定位，至少一位
     * @param d
     * @param accuracy
     * @return
     */
    public static String formatDouble(double d,int accuracy)
    {
        StringBuffer digitCapacity= new StringBuffer();
        digitCapacity.append("0.");
        if(accuracy<=0){
            accuracy=1;
        }
        for(int i=1;i<=accuracy;i++){
            digitCapacity.append("0");
        }
        NumberFormat formatter = new DecimalFormat(digitCapacity.toString());
        return formatter.format(d);
    }
    /**
     * 具有分隔符的字符串，得到其中某个元素子串相邻位置的元素子串
     *
     * @param wholeStr 具有分隔符的字符串
     * @param item     指定的元素子串
     * @param separate 分隔符
     * @param distance 举例指定元素子串的距离，正数为向后n个，负数为向前
     * @return
     */
    public static String getNeighbourItem(String wholeStr, String item, String separate, int distance) {
        String[] strArray = wholeStr.split(separate);
        int curIndex = -1;
        for (int i = 0; i < strArray.length; i++) {
            if (item.equals(strArray[i])) {
                curIndex = i;
                break;
            }
        }
        if (curIndex == -1) {
            return "";
        }
        if (curIndex + distance < 0 || curIndex + distance >= strArray.length) {
            return "";
        }
        return strArray[curIndex + distance];
    }

    /**
     * 具有分隔符的字符串，得到其中某个元素子串之前或者之后的子字符串
     *
     * @param wholeStr 具有分隔符的字符串
     * @param item     指定的元素子串
     * @param separate 分隔符
     * @param front    指定元素前或后的子串，true为向前截取，false为向后截取
     * @return
     */
    public static String getSubStringWithSeparate(String wholeStr, String item, String separate, boolean front) {
        List rtn = new ArrayList();
        if ("".equals(item)){
            return "";
        }

        String[] tmp = wholeStr.split(separate);
        boolean findItem = false;
        for (int k = 0; k < tmp.length; k++) {
            if (front) {
                //该元素前的子串
                if (tmp[k].equals(item)) {
                    break;
                } else {
                    rtn.add(tmp[k]);
                }
            } else {
                //该元素后的子串
                if (!findItem) {
                    if (tmp[k].equals(item)) {
                        findItem = true;
                    }
                } else {
                    rtn.add(tmp[k]);
                }
            }
        }
        return join(",", rtn);
    }

    public static boolean inString(String wholeStr,String item,String separate){
        if("".equals(wholeStr)){
            return false;
        }
        if("".equals(item)){
            return true;
        }
        String[] tmp = wholeStr.split(separate);
        return inArray(item,tmp);
    }

    public static boolean inArray(String str, String[] arr)
    {
        for (int i = 0; i < arr.length; i ++) {
            if (arr[i].equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static int indexOfArray(String str, String[] arr)
    {
        for (int i = 0; i < arr.length; i ++) {
            if (arr[i].equals(str)) {
                return i;
            }
        }
        return -1;
    }
    /**
     *以li分割str字符串，返回字符串数组
     */
    public static String[] split(String li, String str) {
        if((str==null)||(str.trim().length() == 0)){
            str= null;
        }
        return explode(li, str);
    }
    /**
     *以li分割str字符串，返回字符串数组
     */
    public static String[] explode(String li, String str) {
        List rtn = new ArrayList();
        if (str != null) {
            String temp = str;
            for (int i = temp.indexOf(li); i > -1; i = temp.indexOf(li)) {
                rtn.add(temp.substring(0, i));
                temp = temp.substring(i + li.length());
            }
            rtn.add(temp);
        }
        return (String[])rtn.toArray(new String[rtn.size()]);
    }

    /**
     * 两个可以以某分隔符拆分的字符串，从srcStr中排除掉consultStr中包含的元素
     * @param srcStr
     * @param consultStr
     * @param li
     * @return
     */
    public static String getDifferentItems(String srcStr,String consultStr,String li){
        String[] srcArray = split(li,srcStr);
        String[] consultArray = split(li,consultStr);
        List rtnList = new ArrayList();
        for(String item : srcArray){
            if(!inArray(item,consultArray)){
                rtnList.add(item);
            }
        }
        return join(li,rtnList);
    }
    public static List<String> split2List(String li, String str){
        String[] array=split(li, str);
        List<String> list = new ArrayList<>(array.length);
        for(String item :array){
            list.add(item);
        }
        return list;
    }
    public static String agentAddr(String orgAddress){
        if(orgAddress.equals("")){
            return "";
        }
        String[] addr = orgAddress.split("!~!");
        orgAddress= addr[0]+addr[1]+"市"+addr[2]+"区"+addr[3]+addr[4]+"号";
        if(addr.length>5){
            orgAddress= orgAddress +addr[5];
        }
        return orgAddress;
    }
    //首字母转小写
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }
    //首字母转大写
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }
    public static String replaceFirst(String ret, String strToReplace, String replaceWithThis) {
        return ret.replaceFirst(strToReplace, replaceWithThis);
    }

    public static String replaceLast(String text, String strToReplace, String replaceWithThis) {
        return text.replaceFirst("(?s)" + strToReplace + "(?!.*?" + strToReplace + ")", replaceWithThis);
    }
}

