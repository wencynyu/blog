package com.yuwenxin.blog.controller;


import com.yuwenxin.blog.core.Settings;
import com.yuwenxin.blog.model.Article;
import com.yuwenxin.blog.model.Category;
import com.yuwenxin.blog.model.User;
import com.yuwenxin.blog.service.ArticleService;
import com.yuwenxin.blog.service.CategoryService;
import com.yuwenxin.blog.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class CommonController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;

    @RequestMapping(value = {"/","/index"})
    public ModelAndView toIndex(){
        ModelAndView mv = new ModelAndView();

        List<Category> categories = categoryService.findAll();
        categories.remove(categoryService.findById(1)); // 0为无类别属性，这里将其移除
        Map<Category,Article> categoryArticleMap = new HashMap<>();

        for (Category cat :
                categories) {
            Article article = articleService.findPopularestByCategoryName(cat.getCategoryName());
            categoryArticleMap.put(cat,article);
        } // 遍历所有分类并将该分类下最受欢迎（观看数最多）的文章添加到model中，后交由前端模板渲染
        mv.addObject("catArticleMap", categoryArticleMap);

        List<Article> articles = articleService.findPopularestArticles(Settings.DEFAULT_POPULAR_NUM);
        mv.addObject("articles", articles);

        mv.setViewName("index");
        return mv;
    }

    @RequestMapping("/register")
    public ModelAndView toRegister(){
        log.info("跳转至注册页面");
        ModelAndView mv = new ModelAndView();
        mv.addObject("user",new User());
        mv.addObject("errors",new HashMap<>());
        mv.setViewName("user/register");
        return mv;
    }

    @RequestMapping(value = {"/login"})
    public ModelAndView toLogin(){
        log.info("跳转至登录页面");
        ModelAndView mv = new ModelAndView();
        // thymeleaf语法中只能识别已经添加在request中的attribute，若不添加则会报错模板解析错误
        mv.addObject("user",new User());
        mv.addObject("errors",new HashMap<>());
        mv.setViewName("user/login");
        return mv;
    }

    @RequestMapping("/user/changePassword")
    public ModelAndView toChangePassword(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("oldPassword","");
        mv.addObject("newPassword","");
        mv.setViewName("user/change-password");
        return mv;
    }

    @RequestMapping("/noAuth")
    public String toError(){
        return "403";
    }

    @RequestMapping("/404")
    public String to404(){
        return "404";
    }

    @RequestMapping("/aboutMe")
    public ModelAndView toAboutMe(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("about");
        return mv;
    }

}
