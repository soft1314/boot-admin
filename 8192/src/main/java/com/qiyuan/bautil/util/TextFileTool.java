package com.qiyuan.bautil.util;

import java.io.File;
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
}
