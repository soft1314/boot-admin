package com.qiyuan.bautil.util;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class FileTool {


    /**
     * 获取本地文件大小 KB
     * @param fullFileName
     * @return
     * @throws Exception
     */
    public static long getFileSizeKB(String fullFileName) throws Exception{
        File f = new File(fullFileName);
        long  byteCount = 0;
        if (f.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(f);
                byteCount = fis.available();
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }
        return  byteCount;
    }
    /**
     * 根据byte数组，生成文件
     * filePath  文件路径
     * fileName  文件名称（需要带后缀，如*.jpg、*.java、*.xml）
     */
    public static void saveFileFromByteArray(byte[] bfile, String filePath,String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if(!dir.exists() && !dir.isDirectory()){//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 根据 ByteBuffer 数组，生成文件
     * fullPathName  文件路径
     * fileExtName  文件后缀不带点
     * @return
     * @throws Exception
     */
    public static String saveFileFromByteBuffer(ByteBuffer contractFile, String fullPathName, String fileExtName) throws Exception {
        String newFileName = java.util.UUID.randomUUID().toString()+"."+fileExtName;
        String fullPathAndFileName = fullPathName+File.separator+newFileName;
        File saveDir = new File(fullPathName);
        if(!saveDir.exists()){
            saveDir.mkdir();
        }
        File file = new File(fullPathAndFileName);
        boolean append = false;
        FileChannel wChannel = new FileOutputStream(file, append).getChannel();
        wChannel.write(contractFile);
        wChannel.close();
        return fullPathAndFileName;
    }

    /**
     * 将文件读到到byte[]
     * @param filename
     * @return
     * @throws IOException
     */
    public static byte[] toByteArrayFromFile(String filename) throws Exception {
        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(filename, "r").getChannel();
            MappedByteBuffer byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0,
                    fc.size()).load();
            byte[] result = new byte[(int) fc.size()];
            if (byteBuffer.remaining() > 0) {
                byteBuffer.get(result, 0, byteBuffer.remaining());
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                fc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
