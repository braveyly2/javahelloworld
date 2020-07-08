package com.hust.lw.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ApiModel(description="用户实体")
public class User {
    @NotNull
    @ApiModelProperty(value="用户ID", name="id", required=true)
    private Long id;

    @NotNull
    @Size(min=2,max=10)
    @ApiModelProperty(value="用户姓名", name="userName", required=true)
    private String userName;

    @ApiModelProperty("用户密码")
    private String password;

    @Email
    @ApiModelProperty("用户邮箱")
    private String email;
}
