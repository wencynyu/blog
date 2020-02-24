package com.yuwenxin.blog.service.implement;

import com.yuwenxin.blog.core.BaseServiceImpl;
import com.yuwenxin.blog.dao.CommentDao;
import com.yuwenxin.blog.model.Comment;
import com.yuwenxin.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment> implements CommentService {
    @Autowired
    private CommentDao dao;
}
