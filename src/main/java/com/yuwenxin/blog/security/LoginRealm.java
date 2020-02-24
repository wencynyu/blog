package com.yuwenxin.blog.security;

import com.yuwenxin.blog.model.User;
import com.yuwenxin.blog.model.security.Permission;
import com.yuwenxin.blog.model.security.Role;
import com.yuwenxin.blog.service.PermissionService;
import com.yuwenxin.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
public class LoginRealm extends AuthorizingRealm {
    @Autowired
    private UserService service;

    /**
     * 授权 ==> 登录成功后进行权限控制
     * 这里涉及到了user -- user_role -- role -- role_permission -- permission五张表之间的多对多查询
     * 具体细节可以查看相关dao层源码
     *
     * 授权成功后该subject（抽象用户）即有了身份判断，可在前端进行一系列的身份验证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("---登录成功进行授权---");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String username = (String) principalCollection.getPrimaryPrincipal();
        User user = service.findByNameWithAuth(username);
        for (Role role: user.getRoles()){
            if (role.getRoleName()!=null){
                authorizationInfo.addRole(role.getRoleName());
            }
            for (Permission permission :
                    role.getPermissions()) {
                if (permission.getPermissionName()!=null){
                    authorizationInfo.addStringPermission(permission.getPermissionName());
                }
            }
        }
        log.info("---授权成功---");
        return authorizationInfo;
    }

    /**
     * 认证 ==> 登录的实际逻辑实现
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info(authenticationToken.toString());
        User user = service.findByName((String) authenticationToken.getPrincipal());
        // 这里他妈的千万别用log输出信息，log输出不能为空！！！我透！！！
        if (user == null){
            throw new AccountException("用户不存在");
        }

        return new SimpleAuthenticationInfo(
                user.getUsername(), // 用户名
                user.getPassword(), // 密码
                ByteSource.Util.bytes(user.getSalt()), // salt=username+salt
                getName() // realm name
        );

    }
}
