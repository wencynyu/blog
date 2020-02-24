package com.yuwenxin.blog.service.implement;

import com.yuwenxin.blog.core.BaseServiceImpl;
import com.yuwenxin.blog.dao.QuestionDao;
import com.yuwenxin.blog.model.Question;
import com.yuwenxin.blog.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class QuestionServiceImpl extends BaseServiceImpl<Question> implements QuestionService {
    @Autowired
    private QuestionDao dao;
}
