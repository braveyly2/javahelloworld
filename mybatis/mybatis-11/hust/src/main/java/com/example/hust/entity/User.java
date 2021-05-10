package com.example.hust.entity;

/**
 * 类功能描述
 *
 * @author nick.liwei
 * @since 2021-05-07
 */
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;

@Data
public class User {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String name;
    private int age;
    private String email;
    @TableField(fill = INSERT)
    private LocalDateTime createTime;
}
