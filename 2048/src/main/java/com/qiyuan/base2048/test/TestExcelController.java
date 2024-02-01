package com.qiyuan.base2048.test;

import com.gaoice.easyexcel.spring.boot.autoconfigure.ExcelFile;
import com.gaoice.easyexcel.spring.boot.autoconfigure.annotation.ResponseExcel;
import com.gaoice.easyexcel.writer.SheetInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Java知识日历
 *
 * 在 Controller 上使用 @RestController 或者 @ResponseBody 会导致 starter 失效
 */
@Controller
@RequestMapping("/api/system/free/test")
public class TestExcelController {
    /**
     * 注解 @ResponseExcel 的必须值：classFieldNames
     * sheetName 默认值为 default
     * fileName 默认使用 sheetName 的值
     * columnNames 默认使用 classFieldNames 的值
     * sheetStyle 可以指定样式，默认为 DefaultSheetStyle.class
     * 生效条件：
     * enable-response-excel=true
     * 使用 @ResponseExcel 注解
     * 不使用 @ResponseBody 注解
     *
     * @return List
     */
    @RequestMapping("/downloadExcel")
    @ResponseExcel(fileName="Java知识日历20201101测试",sheetName = "同一班的同学名册",columnNames= {"学生姓名","学号","年龄"},classFieldNames = { "name","stuNo","age" })
    public List<Student> list() {
        return getStudents();
    }
    /**
     * 生成测试数据
     *
     * @return List
     */
    private List<Student> getStudents() {
        List<Student> list = new ArrayList<Student>();
        Student stu = new Student();
        stu.setName("Java知识日历");
        stu.setAge(3);
        stu.setStuNo("A0001");
        ClassTeacher classTeacher = new ClassTeacher();
        classTeacher.setName("Java课老师");
        stu.setClassTeacher(classTeacher);
        list.add(stu);
        for(int i = 0;i<10;i++) {
            Student testStu = new Student();
            testStu.setName("小明"+i+"号" );
            testStu.setAge(6+i);
            testStu.setStuNo("A000"+(i+2));
            ClassTeacher classTeacher1 = new ClassTeacher();
            classTeacher1.setName((i+1)+"课老师");
            testStu.setClassTeacher(classTeacher1);
            list.add(testStu);
        }
        return list;
    }

    /***其他特殊需要定制的需求。可以灵活组合，满足你个性定制的需求***/

    /**
     * 文件名默认使用 sheetName
     * 生效条件：
     * enable-sheet-info=true
     * 返回值类型为 SheetInfo
     * 不使用 @ResponseBody 注解
     *
     * @return SheetInfo
     */
    @RequestMapping("/sheetInfo")
    public SheetInfo sheetInfo() {
        String sheetName = "灵活组合测试";
        String[] columnNames = {"学生姓名", "学号", "年龄"};
        String[] classFieldNames = {"name", "stuNo", "age"};
        List<Student> data = getStudents();
        return new SheetInfo(sheetName, columnNames, classFieldNames, data);
    }

    /**
     * 生效条件：
     * enable-excel-file=true
     * 返回值类型为 ExcelFile
     * 不使用 @ResponseBody 注解
     *
     * @return ExcelFile
     */
    @RequestMapping("/excelFile")
    public ExcelFile excelFile() {
        /* SheetInfo */
        String sheetName = "灵活组合测试Sheet1";
        String[] columnNames = {"学生姓名", "学号", "年龄"};
        String[] classFieldNames = {"name", "stuNo", "age"};
        List<Student> data = getStudents();
        SheetInfo sheetInfo1 = new SheetInfo(sheetName, columnNames, classFieldNames, data);

        String sheetName2 = "灵活组合测试Sheet2";
        String[] columnNames2 = {"学生姓名", "学号", "年龄","班主任姓名"};
        String[] classFieldNames2 = {"name", "stuNo", "age","classTeacher.name"};
        SheetInfo sheetInfo2 = new SheetInfo(sheetName2, columnNames2, classFieldNames2, data);

        /* 通过 ExcelFile 自定义下载的文件名，放入多个 sheet */
        ExcelFile excelFile = new ExcelFile();
        excelFile.setFileName("ExcelFile可以自定义文件名和放入多个sheet");
        excelFile.addSheet(sheetInfo1);
        excelFile.addSheet(sheetInfo2);

        return excelFile;
    }


}

