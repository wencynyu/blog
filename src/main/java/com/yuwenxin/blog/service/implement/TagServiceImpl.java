package com.yuwenxin.blog.service.implement;

import com.yuwenxin.blog.core.BaseServiceImpl;
import com.yuwenxin.blog.dao.TagDao;
import com.yuwenxin.blog.model.Tag;
import com.yuwenxin.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TagServiceImpl extends BaseServiceImpl<Tag> implements TagService {
    @Autowired
    private TagDao dao;
}
