package com.example.hust.entity;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 类功能描述
 *
 * @author nick.liwei
 * @since 2021-05-10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("user")
public class Teacher extends Model<Teacher> {
    private Long id;
    //@TableField(condition = SqlCondition.LIKE)
    private String name;
    private int age;
    private String email;
    @TableField(exist = false)
    private String school;
}
