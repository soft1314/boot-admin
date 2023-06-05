package com.qiyuan.base2048.service.component;

import com.qiyuan.bautil.util.FileNameTool;
import com.qiyuan.bautil.util.TimeTool;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@Configuration
public class MinIoUtils {
    @Value("${minio.url}")
    private String url;
    @Value("${minio.accessKey}")
    private String accessKey;
    @Value("${minio.secreKey}")
    private String secreKey;
    private MinioClient instance;

    @PostConstruct
    public void init() throws Exception {
        instance = new MinioClient(url, accessKey, secreKey);
    }

    /**
     * 判断 bucket是否存在
     *
     * @param bucketName
     * @return
     */
    public boolean bucketExists(String bucketName) throws Exception {
        return instance.bucketExists(bucketName);
    }

    /**
     * 创建 bucket
     *
     * @param bucketName
     */
    public void makeBucket(String bucketName) throws Exception {
        boolean isExist = instance.bucketExists(bucketName);
        if (!isExist) {
            instance.makeBucket(bucketName);
        }
    }

    /**
     * 文件上传
     *
     * @param bucketName
     * @param objectName
     * @param filename
     */
    public void putObject(String bucketName, String objectName, String filename) throws Exception {
        makeBucket(bucketName);
        instance.putObject(bucketName, objectName, filename, null);
    }

    /**
     * 文件上传
     *
     * @param bucketName
     * @param multipartFile
     */
    public void putObject(String bucketName, MultipartFile multipartFile, String filename) throws Exception {
        makeBucket(bucketName);
        // PutObjectOptions，上传配置(文件大小，内存中文件分片大小)
        PutObjectOptions putObjectOptions = new PutObjectOptions(multipartFile.getSize(), PutObjectOptions.MIN_MULTIPART_SIZE);
        // 文件的ContentType
        putObjectOptions.setContentType(multipartFile.getContentType());
        instance.putObject(bucketName, filename, multipartFile.getInputStream(), putObjectOptions);
    }

    /**
     * 删除文件
     * 删除保留 异常处理
     */
    public boolean removeObject(String bucketName, String objectName) {
        boolean flag = true;
        try {
            instance.removeObject(bucketName, objectName);
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 文件下载
     * @param bucketName
     * @param fileName
     * @param originalName
     * @param response
     * @throws Exception
     */
    public void downloadFile(String bucketName, String fileName, String originalName, HttpServletResponse response) throws Exception {
        InputStream file = instance.getObject(bucketName, fileName);
        String showFileName = new String(fileName.getBytes("ISO8859-1"), StandardCharsets.UTF_8);
        if (StringUtils.isNotEmpty(originalName)) {
            showFileName = java.net.URLEncoder.encode(originalName, "UTF8");
        }
        response.setHeader("Content-Disposition", "attachment;filename=" + showFileName);
        ServletOutputStream servletOutputStream = response.getOutputStream();
        int len;
        byte[] buffer = new byte[1024];
        while ((len = file.read(buffer)) > 0) {
            servletOutputStream.write(buffer, 0, len);
        }
        servletOutputStream.flush();
        file.close();
        servletOutputStream.close();
    }

    /**
     * 生成缺省桶名称
     * @return
     * @throws Exception
     */
    public String getMinioDefaultBucketName() throws Exception{
        String yyyy = TimeTool.stringOfDateTimeYYYY();
        String mm = TimeTool.stringOfDateTimeMM();
        return yyyy+mm;
    }

    /**
     * 生成缺省文件名
     * @param fileName
     * @return
     * @throws Exception
     */
    public String getMinioDefaultFileName(String fileName) throws Exception{
        String uuid = java.util.UUID.randomUUID().toString().replace("-","");
        String extName = FileNameTool.getFileExtName(fileName);
        return uuid + extName;
    }
    /**
     * 获取BASE64文件流
     * @param bucketName
     * @param fileName
     * @throws Exception
     */
    public String downloadBase64File(String bucketName, String fileName) throws Exception {
        InputStream file = instance.getObject(bucketName, fileName);
        return Base64.getEncoder().encodeToString(inputStreamToBytes(file));
    }
    /**
     * inputStream 转化成 bytes
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] inputStreamToBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[4096];
        int index = 0;
        while((index = inputStream.read(buff, 0, 4096)) > 0) {
            baos.write(buff, 0, index);
        }
        return baos.toByteArray();
    }

    public InputStream getInputStream(String bucketName, String fileName) throws Exception{
        InputStream is = instance.getObject(bucketName, fileName);
        return is;
    }
}
