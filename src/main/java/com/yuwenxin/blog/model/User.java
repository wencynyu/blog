package com.yuwenxin.blog.model;

import com.yuwenxin.blog.model.enumpkg.Gender;
import com.yuwenxin.blog.model.security.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 一些基本属性：
     * id（主键），用户名，密码，电子邮箱，电话号码，性别
     * 注册时间，最近登录时间
     * 是否为管理员，是否通过验证
     */
    private Integer iduser;
    private String username;
    private String password;
    private String passwordConfirm;  // 该字段用于确认密码，数据库中不需要这个字段
    private String email;
    private String phoneNumber;
    private Gender gender;

    private Date registerTime;
    private Date latestLoginDate;

    private boolean isAdmin;
    private boolean isChecked;

    private String salt;
    private Integer status;
    private Set<Role> roles;

    /**
     * todo:实现用户与用户之间的好友关系，关注关系，在线聊天讨论功能（可基于websocket实现http长连接的全双工通信）
     */

}
