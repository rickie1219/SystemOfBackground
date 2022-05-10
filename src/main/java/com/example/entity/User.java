package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;


@Data
@TableName(value = "tb_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    // 注解： 忽略某个字段，不展示给前端
    //@JsonIgnore
    private String password;
    private String nickname;
    private String phone;
    private String email;
    @TableField(value = "address") // 指定数据库的字段名称
    private String address;
}
