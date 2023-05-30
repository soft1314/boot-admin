package com.qiyuan.bautil.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TextFileTool {
    public static String sm3(File file) throws Exception{
        String data = new String(Files.readAllBytes(Paths.get(file.getPath())));
        return cn.hutool.crypto.SmUtil.sm3(data);
    }
    public static String sm3(String fullFileName) throws Exception{
        String data = new String(Files.readAllBytes(Paths.get(fullFileName)));
        return cn.hutool.crypto.SmUtil.sm3(data);
    }

    /**
     * 读文件文件
     * @param file
     * @return
     * @throws IOException
     */
    public static String readTxt(File file) throws IOException {
        String s = "";
        InputStreamReader in = new InputStreamReader(new FileInputStream(file),"UTF-8");
        BufferedReader br = new BufferedReader(in);
        StringBuffer content = new StringBuffer();
        while ((s=br.readLine())!=null){
            content = content.append(s);
        }
        return content.toString();
    }
}
