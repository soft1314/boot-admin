package com.qiyuan.base2048.test;

/**
 * @author Java知识日历
 */
public class Student {
    private String name;
    private String stuNo;
    private int age;

    private ClassTeacher classTeacher;//该学生的班主任
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getStuNo() {
        return stuNo;
    }
    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public ClassTeacher getClassTeacher() {
        return classTeacher;
    }
    public void setClassTeacher(ClassTeacher classTeacher) {
        this.classTeacher = classTeacher;
    }
}

