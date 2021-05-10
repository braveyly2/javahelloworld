package com.example.hust.entity;

/**
 * 类功能描述
 *
 * @author nick.liwei
 * @since 2021-05-07
 */
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class Student {
    @TableId("id")
    private Long student_id;
    @TableField("name")
    private String real_name;
    private int age;
    private String email;
    @TableField(exist = false)
    private String school;
}
