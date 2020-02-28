package com.yuwenxin.blog.controller;

import com.yuwenxin.blog.model.Comment;
import com.yuwenxin.blog.model.User;
import com.yuwenxin.blog.service.CommentService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService service;

    @RequestMapping("/doPost")
    public ModelAndView doPost(@ModelAttribute("comment")Comment comment){
        ModelAndView mv = new ModelAndView();
        Session session = SecurityUtils.getSubject().getSession();
        User poster = (User) session.getAttribute("user");
        comment.setBelongedPosterId(poster.getIduser());
        comment.setPostDate(new Date());
        service.insert(comment);
        mv.setViewName("article/single-post");
        return mv;
    }

    @RequestMapping("/delete/{id}")
    public ModelAndView doDelete(@PathVariable("id") Integer id){
        ModelAndView mv = new ModelAndView();
        service.deleteById(id);
        mv.setViewName("index");  // 这里删除评论后直接转到主页
        return mv;
    }
}
