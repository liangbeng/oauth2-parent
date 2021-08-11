package com.example.demo.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_user")
public class User  implements Serializable {
    private static final long serialVersionUID = 2646549708067524760L;
    @TableId(value = "id")
    @TableField(value = "id")
    private Integer id;

    @TableField(value = "username")
    private String username;

    @TableField(value = "password")
    private String password;

    @TableField(value = "nickname")
    private String nickname;

    @TableField(value = "headimgurl")
    private String headimgurl;

    @TableField(value = "phone")
    private String phone;

    @TableField(value = "telephone")
    private String telephone;

    @TableField(value = "email")
    private String email;

    @TableField(value = "birthday")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @TableField(value = "sex")
    private Integer sex;

    @TableField(value = "status")
    private Integer status;

    @TableField(value = "createtime")
    private Date createtime;

    @TableField(value = "updatetime")
    private Date updatetime;

    public interface Status {
        int DISABLED = 0;
        int VALID = 1;
        int LOCKED = 2;
    }
}