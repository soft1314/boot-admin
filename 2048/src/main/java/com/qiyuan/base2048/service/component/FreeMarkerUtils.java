package com.qiyuan.base2048.service.component;

import com.qiyuan.base2048.pojo.vo.vuecomponent.UserTableColumnResultDTO;
import com.qiyuan.base2048.mapper.mybatis.UserTableColumnMasterGetter;
import com.qiyuan.base2048.service.generator.TxtFileSummaryGetter;
import com.qiyuan.base2048.service.generator.TxtFileSummarySaver;
import com.qiyuan.bautil.util.TimeTool;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 1. 用来判断包路径是否存在，若不存在，即创建文件夹；
 * 2. 将配好的模板文件写出到指定路径下的文件
 * 3. 从数据库获取表信息
 */
@Component
public class FreeMarkerUtils {
    @Value("${base2048.table-schema}")
    private String tableSchema;
    @Autowired
    Configuration freeMarker;

    @Resource
    private UserTableColumnMasterGetter userTableColumnMasterGetter;
    @Resource
    private TxtFileSummaryGetter txtFileSummaryGetter;
    @Resource
    private TxtFileSummarySaver txtFileSummarySaver;

    /**
     * 判断包路径是否存在
     */
    private void pathJudgeExist(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 输出到文件
     */
    public void printFile(Map<String, Object> root, Template template, String filePath, String fileName) throws Exception {
        pathJudgeExist(filePath);
        File file = new File(filePath, fileName);
        /** 如果存在，判断校验码是否修改过，如果修改过，就备份另存 **/
        if (file.exists()) {
            String fileSummary = txtFileSummaryGetter.fromIo(file.getPath());
            String dbSummary = txtFileSummaryGetter.fromDb(file.getPath());
            if(dbSummary.equals(fileSummary)){
                file.createNewFile();
            }else{
                String bakFileName = file.getPath()+".bak"+ TimeTool.stringOfDateTimeyyyyMMddHHmmss(new Date());
                File bakFile = new File(bakFileName);
                file.renameTo(bakFile);
                txtFileSummarySaver.backup(file.getPath(),bakFileName);
                file.createNewFile();
            }
        }else{
            file.createNewFile();
        }
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
        template.process(root, out);
        out.close();
        String summary = txtFileSummaryGetter.fromIo(file.getPath());
        txtFileSummarySaver.save(file.getPath(),summary);
    }

    /**
     * 首字母大写
     */
    public String capFirst(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    /**
     * 下划线命名转为驼峰命名
     */
    public String underlineToHump(String para) {
        StringBuilder result = new StringBuilder();
        String[] a = para.split("_");
        for (String s : a) {
            if (result.length() == 0) {
                result.append(s.toLowerCase());
            } else {
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 下划线命名转为驼峰命名---首字母大写
     */
    public String toHump(String para) {
        StringBuilder result = new StringBuilder();
        String[] a = para.split("_");
        for (String s : a) {
            result.append(s.substring(0, 1).toUpperCase());
            result.append(s.substring(1).toLowerCase());
        }
        return result.toString();
    }

    /**
     * 获取类名
     */
    public String getEntityName(String tableName) {
        return underlineToHump(capFirst(tableName.toLowerCase()));
    }

    /**
     * 获取首字母小写类名
     */
    public String getEntityNameLower(String tableName) {
        return underlineToHump(tableName.toLowerCase());
    }

    /**
     * 将[数据库类型]转换成[Java类型],如果遇到没有写的类型,会出现Undefine,在后面补充即可
     */
    public String convertToJava(String columnType) {
        String result;
        switch (columnType) {
            case "VARCHAR": {
                result = "String";
                break;
            }
            case "VARCHAR2": {
                result = "String";
                break;
            }
            case "NUMBER": {
                result = "BigDecimal";
                break;
            }
            case "DATE": {
                result = "Date";
                break;
            }
            case "INT": {
                result = "Integer";
                break;
            }
            case "BIGINT": {
                result = "Long";
                break;
            }
            case "FLOAT": {
                result = "Float";
                break;
            }
            case "DOUBLE": {
                result = "Double";
                break;
            }
            case "DATETIME": {
                result = "Date";
                break;
            }
            case "BIT": {
                result = "Boolean";
                break;
            }
            default: {
                result = "Undefine";
                break;
            }
        }
        return result;
    }

    /**
     * 匹配字符串中的英文字符
     */
    public String matchResult(String str) {
        String regEx2 = "[a-z||A-Z]";
        Pattern pattern = Pattern.compile(regEx2);
        StringBuilder sb = new StringBuilder();
        Matcher m = pattern.matcher(str);
        while (m.find()) {
            for (int i = 0; i <= m.groupCount(); i++) {
                sb.append(m.group());
            }
        }
        return sb.toString();
    }

    /**
     * 获取表信息
     */
    public List<UserTableColumnResultDTO> getDataInfo(String tableName) {
        List<UserTableColumnResultDTO> userTableColumnList = userTableColumnMasterGetter.selectUserTableColumn(tableSchema,tableName);
        for (UserTableColumnResultDTO userTableColumn : userTableColumnList) {
            userTableColumn.setDataType(this.convertToJava(userTableColumn.getDataType()));
            userTableColumn.setColumnName(this.underlineToHump(userTableColumn.getColumnName()));
        }
        return userTableColumnList;
    }

    /**
     * 生成代码
     */
    public void generate(Map<String, Object> root, String templateName, String saveUrl, String entityName) throws Exception {
        String path = "generator/";
        //获取模板
        Template template = freeMarker.getTemplate(path+templateName);
        //输出文件
        printFile(root, template, saveUrl, entityName);
    }
    public String generateSource(Map<String, Object> root, String templateName) throws Exception {
        String path = "generator/";
        //获取模板
        Template template = freeMarker.getTemplate(path+templateName);
        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        template.process(root, writer);
        StringReader reader = new StringReader(stringWriter.toString());
        writer.flush();
        writer.close();
        BufferedReader r = new BufferedReader(reader);
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = r.readLine()) != null) {
            sb.append(line);
            sb.append("\r\n");
        }
        String source = sb.toString();
        return source;
    }

}

