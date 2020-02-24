package com.yuwenxin.blog.service;

import com.yuwenxin.blog.core.BaseService;
import com.yuwenxin.blog.model.User;

public interface UserService extends BaseService<User> {
    void register(User user_in);
    User findByNameWithAuth(String name);
//    void login(User user_in);  // login交由shiro框架实现 ==》 LoginRealm
}
