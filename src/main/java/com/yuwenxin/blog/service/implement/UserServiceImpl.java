package com.yuwenxin.blog.service.implement;

import com.yuwenxin.blog.core.BaseServiceImpl;
import com.yuwenxin.blog.core.Tools;
import com.yuwenxin.blog.dao.PermissionDao;
import com.yuwenxin.blog.dao.RoleDao;
import com.yuwenxin.blog.dao.UserDao;
import com.yuwenxin.blog.model.User;
import com.yuwenxin.blog.model.security.Permission;
import com.yuwenxin.blog.model.security.Role;
import com.yuwenxin.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Service
@Slf4j
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    @Autowired
    private UserDao dao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

    public void register(User user_in){
        Tools encryptionUtil = new Tools();
        Map<String, String> encrypt = encryptionUtil.encryption(user_in.getPassword());  // 这个map用来保存注册时的salt和加密密码
        user_in.setPassword(encrypt.get("password"));
        user_in.setSalt(encrypt.get("salt"));
        dao.insert(user_in);
    }

    @Override
    public User findByNameWithAuth(String name) {
        User user = dao.findByName(name);

        // 多表关联查询，实现user -- roles -- permissions的多对多查询
        // 根据usertorole表，已知userid，可查询到所有的roleid。 而后根据roleid查询到role对象
        // 根据roletopermission表，已知roleid，可查询到所有的permissionid。 而后根据permissionid查询到permission对象
        Set<Role> roleSet = roleDao.findRolesByUserid(user.getIduser());

        for (Role r :
                roleSet) {
            Set<Permission> permissions = permissionDao.findPermissionByRoleid(r.getIdrole());
            r.setPermissions(permissions);
        }
        user.setRoles(roleSet);
        return user;
    }

}
