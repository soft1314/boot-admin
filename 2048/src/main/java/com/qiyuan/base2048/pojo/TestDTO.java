package com.qiyuan.base2048.pojo;

import com.qiyuan.bautil.annotate.valid.BooleanValid;
import com.qiyuan.bautil.annotate.valid.EnumValid;
import com.qiyuan.bautil.annotate.valid.OverallValid;
import com.qiyuan.bautil.annotate.valid.StringLength;
import com.qiyuan.bautil.enums.SexEnum;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Data
/** 组合校验注解（方式1） **/
@OverallValid(value = "check1" ,message="女士不得小于16岁。")
@OverallValid(value = "check2" ,message="男士不得小于18岁。")
public class TestDTO {
    //字符个数检测（内置注解）
    @Size(min = 1,max = 10,message = "姓名长度必须为1到10")
    //占用空间长度检测（自定义注解）
    @StringLength(min = 1,max = 12,message = "姓名的保存长度不允许超过12个字节。")
    private String name;
    //利用枚举类检测（自定义注解）
    @EnumValid(target = SexEnum.class, message = "性别的取值范围是【1】和【2】")
    private String sex;
    //注意 @NotNull @NotEmpty @NotBlank 的区别
    @NotBlank(message = "姓氏是必填项。")
    private String firstName;

    @Min(value = 10,message = "年龄最小为10")
    @Max(value = 100,message = "年龄最大为100")
    private Integer age;

    @Past(message = "出生时间必须为过去时间")
    private Date birth;

    @NotEmpty(message = "兴趣不能为空")
    private List<String> interest;

    //嵌套检测
    @Valid
    private List<TestDTO> children;
    @Valid
    private TestDTO father;
    @Valid
    private TestDTO mother;

    /** 组合校验（方式2） **/
    @BooleanValid(message = "男性年龄需在60岁以下")
    public boolean getValid1(){
        if(sex.equalsIgnoreCase("1") && age >= 60 ){
            return false;
        }
        return true;
    }
    /** 组合校验（方式2） **/
    @BooleanValid(message = "女性年龄需在55岁以下")
    public boolean getValid2(){
        if(sex.equalsIgnoreCase("2") && age >= 55 ){
            return false;
        }
        return true;
    }
    /** 组合校验（方式1）方法 **/
    public boolean check1(){
        if(sex.equalsIgnoreCase("2") && age < 16 ){
            return false;
        }
        return true;
    }
    /** 组合校验（方式1）方法 **/
    public boolean check2(){
        if(sex.equalsIgnoreCase("1") && age < 18 ){
            return false;
        }
        return true;
    }
}
