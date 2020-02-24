package com.yuwenxin.blog.service.implement;

import com.yuwenxin.blog.core.BaseServiceImpl;
import com.yuwenxin.blog.dao.CategoryDao;
import com.yuwenxin.blog.model.Category;
import com.yuwenxin.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category> implements CategoryService {
    @Autowired
    private CategoryDao dao;
}
