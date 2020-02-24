package com.yuwenxin.blog.controller;

import com.yuwenxin.blog.core.PageUtil;
import com.yuwenxin.blog.model.Article;
import com.yuwenxin.blog.model.Category;
import com.yuwenxin.blog.model.Comment;
import com.yuwenxin.blog.service.ArticleService;
import com.yuwenxin.blog.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Controller
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService service;

    @RequestMapping("/view/all-blogs")
    public ModelAndView allBlog(@RequestParam(value = "page", defaultValue = "1")Integer page){
        ModelAndView mv = new ModelAndView();
        PageUtil<Article> articlePageUtil = service.findAllByPageUtilInv(page);

        // 添加当前页的全部内容
        List<Article> articleWithCate = articlePageUtil.getTableList();
        mv.addObject("articleWithCate", articleWithCate);

        mv.setViewName("article/all-blogs");
        return mv;
    }

    @RequestMapping("/view/detail/{articleName}")
    public ModelAndView toBlog(@PathVariable("articleName")String articleName){
        ModelAndView mv = new ModelAndView();
        Article article = service.findByName(articleName);
        mv.addObject("article",article);


        mv.addObject("comment",new Comment());
        mv.setViewName("article/single-post");
        return mv;
    }

    @RequestMapping("/view/category/{categoryName}/{page}")
    public ModelAndView toCategory(@PathVariable("categoryName") String categoryName,@PathVariable("page")Integer page){
        ModelAndView mv = new ModelAndView();

        PageUtil<Article> catArticles = service.findAllByCategoryAndPageUtil(categoryName, page);
        List<Article> articles = catArticles.getTableList();
        mv.addObject("articles",articles);
        mv.setViewName("article/cate-blogs");
        return mv;
    }

    @RequestMapping("/search/{searchName}/{page}")
    public ModelAndView doSearch(@PathVariable("searchName") String searchName,@PathVariable("page") Integer page){
        ModelAndView mv = new ModelAndView();

        PageUtil<Article> articlePageUtil = service.fuzzyFindByContentAndPageUtil(searchName, page);
        List<Article> articles = articlePageUtil.getTableList();
        mv.addObject("searchName",searchName);
        mv.addObject("articles",articles);
        mv.setViewName("article/search-blogs");

        return mv;

    }
}
