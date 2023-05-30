package com.qiyuan.bautil.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileNameTool {
    /**
     * 返回文件名(去掉路径)
     * 路径分隔符，不能用服务器中，要用客户端中的，所以 /  \ 都处理吧
     * @param fullFileName String
     * @return String
     */
    public static String getFileName(String fullFileName) {
        String ret = fullFileName;
        int pospoint = fullFileName.lastIndexOf("/")+1;
        if (pospoint > 0) {
            ret = fullFileName.substring(pospoint);
        }
        pospoint = fullFileName.lastIndexOf("\\")+1;
        if (pospoint > 0) {
            ret = fullFileName.substring(pospoint);
        }
        return ret;
    }

    /**
     * 返回不带后缀的纯文件名
     * @param fullFileName
     * @return
     */
    public static String getFileNameWithoutExt(String fullFileName) {
        String ret = FileNameTool.getFileName(fullFileName);

        int pospoint = ret.lastIndexOf(".");
        if (pospoint > 0) {
            ret = ret.substring(0,pospoint);
        }
        return ret;
    }
    /**
     * 返回文件路径
     * @param fullFileName
     * @return
     */
    public static String getFilePath(String fullFileName){
        String ret = "";
        int pospoint = fullFileName.lastIndexOf("/");
        if (pospoint > 0) {
            ret = fullFileName.substring(0,pospoint);
        }
        return ret;
    }

    /**
     * 返回文件扩展名，带.
     * @param filename String
     * @return String
     */
    public static String getFileExtName(String filename) {
        String ret = "";
        int pospoint = filename.lastIndexOf(".");
        if (pospoint > 0) {
            ret = filename.substring(pospoint);
        }
        return ret;
    }

    /**
     * 组成完成的文件全名（包括目录）
     * 自动处理文件分隔符
     * @param mainPpath
     * @param subPath
     * @param fileName
     * @return
     */
    public static String getFullName(String mainPpath,String subPath,String fileName){
        String ret="";
        if(mainPpath.endsWith(File.separator)){
            ret=mainPpath+subPath;
        }else{
            ret=mainPpath+File.separator+subPath;
        }
        if(ret.endsWith(File.separator)){
            ret=ret+fileName;
        }else{
            ret=ret+File.separator+fileName;
        }
        return ret;
    }

    /**
     * 连接目录名
     * @param mainPath
     * @param subPath
     * @return
     */
    public static String appendDirectoryName(String mainPath,String subPath){
        String ret="";
        if(mainPath.endsWith(File.separator)){
            ret=mainPath+subPath;
        }else{
            ret=mainPath+File.separator+subPath;
        }
        return ret;
    }

    /**
     * 在指定文件夹下检查，创建年，月日文件夹
     * @param mainPath
     * @param fileName
     * @throws Exception
     */
    public static String createSubDirectoryByDate(String mainPath,String fileName) throws Exception{
        String yyyy= TimeTool.stringOfDateTimeYYYY();
        String mm= TimeTool.stringOfDateTimeMM();
        String dd= TimeTool.stringOfDateTimeDD();

        String fullName=FileNameTool.getFullName(mainPath,yyyy+ File.separator+mm+File.separator+dd,fileName);
        Path path = Paths.get(fullName);
        //如果没有files文件夹，则创建
        if (!Files.isWritable(path)) {
            String fullPath=FileNameTool.appendDirectoryName(mainPath,yyyy+ File.separator+mm+File.separator+dd);
            Files.createDirectories(Paths.get(fullPath));
        }
        return fullName;
    }

    /**
     * 生成随机文件名，后缀不变
     * @param originalFilename
     * @return
     */
    public static String getNewFileNameForSave(String originalFilename){
        String ext=FileNameTool.getFileExtName(originalFilename);
        return java.util.UUID.randomUUID().toString()+ext;
    }
    /**
     * 返回相对URL，即从绝对路径串去掉前一段主文件夹串
     * @param mainPath
     * @param filename
     * @return
     */
    public static String getReferUrl(String mainPath,String filename) {
        String ret = "";
        ret = filename.replace(mainPath,"");
        return ret;
    }

    /**
     *
     * @param url
     * @return
     */
    public static String delFirstPathSeparator(String url){
        if (url.startsWith("/")) {
            url = url.substring(1);
        }
        if (url.startsWith("\\")) {
            url = url.substring(1);
        }
        return url;
    }
    /**
     * 物理路径转URL相对路径
     * @param mainPath
     * @param filename
     * @return
     */
    public static String directory2Rul(String mainPath,String filename){
        return "/api/file/"+ FileNameTool.getReferUrl(mainPath,filename);
    }

}
