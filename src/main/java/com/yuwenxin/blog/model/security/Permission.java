package com.yuwenxin.blog.model.security;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Permission {

    private Integer idpermission;
    private String permissionName;
    private String url;
    private String description;

    private Date createTime;

    private List<Role> roles;

}
