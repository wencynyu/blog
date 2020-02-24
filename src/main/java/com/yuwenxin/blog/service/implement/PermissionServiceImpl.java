package com.yuwenxin.blog.service.implement;

import com.yuwenxin.blog.core.BaseService;
import com.yuwenxin.blog.core.BaseServiceImpl;
import com.yuwenxin.blog.dao.PermissionDao;
import com.yuwenxin.blog.model.security.Permission;
import com.yuwenxin.blog.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {
    @Autowired
    private PermissionDao dao;
}
