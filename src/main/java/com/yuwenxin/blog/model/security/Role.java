package com.yuwenxin.blog.model.security;

import com.yuwenxin.blog.model.User;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;


@Data
public class Role {

    private Integer idrole;
    private String roleName;
    private String description;

    private Date createTime;

    private Set<Permission> permissions;
    private List<User> users;

}
