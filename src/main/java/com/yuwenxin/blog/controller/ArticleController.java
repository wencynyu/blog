package com.yuwenxin.blog.controller;

import com.yuwenxin.blog.core.PageUtil;
import com.yuwenxin.blog.model.Article;
import com.yuwenxin.blog.model.Category;
import com.yuwenxin.blog.model.Comment;
import com.yuwenxin.blog.service.ArticleService;
import com.yuwenxin.blog.service.CategoryService;
import com.yuwenxin.blog.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Controller
@RequestMapping("/article")
public class ArticleController {
    /**
     * 忽略了当数据库刚创建时表中无内容造成的查询空列表问题
     * 当系统投入使用时默认所有表项都不为空
     * 所有类别下均有文章（否则没有创建类别的必要 ==》 不符合实际使用逻辑）
     */
    @Autowired
    private ArticleService service;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/view/all-blogs")
    public ModelAndView allBlog(@RequestParam(value = "page", defaultValue = "1")Integer page){
        ModelAndView mv = new ModelAndView();
        PageUtil<Article> articlePageUtil = service.findAllByPageUtilImpl(page);
        // 添加当前页的全部内容
        mv.addObject("articlePageUtil", articlePageUtil);
        mv.setViewName("article/all-blogs");
        return mv;
    }

    @RequestMapping("/view/detail/{articleName}")
    public ModelAndView toBlog(@PathVariable("articleName")String articleName){
        ModelAndView mv = new ModelAndView();
        Article article = service.findByName(articleName);
        if (article == null){
            mv.setViewName("redirect:/404");
            return mv;
        }
        mv.addObject("article",article);
        mv.addObject("comment",new Comment());
        mv.setViewName("article/single-post");
        article.setWatchedNum(article.getWatchedNum() + 1);
        service.update(article);
        return mv;
    }

    @RequestMapping("/view/category/{categoryName}")
    public ModelAndView toCategory(@PathVariable("categoryName") String categoryName,@RequestParam(value = "page", defaultValue = "1")Integer page){
        /**
         * 这里为什么不用CategoryService来获取类别并且在获取的Category对象中取出ArticleList呢？
         * 因为此处需求是根据类别名称来查询文章并进行分页处理
         * 如果采用category对象来setArticleList，会造成一次直接查询该类别的全部文章，给数据库io造成额外的压力
         * 而非要在category对象中进行分页setArticleList，又会导致逻辑实现晦涩难懂
         */
        ModelAndView mv = new ModelAndView();
        PageUtil<Article> articlePageUtil = service.findAllByCategoryAndPageUtil(categoryName, page);
        if (articlePageUtil.getTableList() == null){
            mv.setViewName("redirect:/404");
            return mv;
        }
        mv.addObject("articlePageUtil",articlePageUtil);
        mv.addObject("cateName",categoryName);
        mv.setViewName("article/cate-blogs");
        return mv;
    }

    @RequestMapping("/search")
    public ModelAndView doSearch(@RequestParam("searchName") String searchName,@RequestParam(value = "page",defaultValue = "1") Integer page){
        ModelAndView mv = new ModelAndView();

        PageUtil<Article> articlePageUtil = service.fuzzyFindByContentAndPageUtil(searchName, page);
        if (articlePageUtil.getTableList() == null){
            mv.setViewName("redirect:/404");
            return mv;
        }
        mv.addObject("searchName",searchName);
        mv.addObject("articlePageUtil",articlePageUtil);
        mv.setViewName("article/search-blogs");
        return mv;

    }

    @RequestMapping("/delete/{id}")
    public ModelAndView doDelete(@PathVariable("id") Integer id){
        ModelAndView mv = new ModelAndView();
        service.deleteById(id);
        PageUtil<Article> articlePageUtil = service.findAllByPageUtilImpl(1);  // 返回第一页
        mv.addObject("articlePageUtil", articlePageUtil);
        mv.setViewName("article/all-blogs");
        return mv;
    }

    @RequestMapping("/update/to/{id}")
    public ModelAndView toUpdate(@PathVariable("id") Integer id){
        ModelAndView mv = new ModelAndView();
        mv.addObject("article", service.findById(id));
        mv.setViewName("article/edit-blog");
        return mv;
    }

    @RequestMapping("/update/do/{id}")
    public ModelAndView doUpdate(@PathVariable("id") Integer id,@ModelAttribute("article") Article article){
        ModelAndView mv = new ModelAndView();
        service.update(article);
        mv.addObject("article", article);
        mv.setViewName("article/single-post");
        return mv;
    }

    @RequestMapping("/add")
    public ModelAndView toAdd(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("article",new Article());
        mv.addObject("cates",categoryService.findAll());
        mv.setViewName("article/add-blog");
        return mv;
    }

    @RequestMapping("/doAdd")
    public ModelAndView doAdd(@ModelAttribute("article") Article article,@ModelAttribute("cateName") String cateName){
        ModelAndView mv = new ModelAndView();
        article.setPostTime(new Date());
        article.setBelongedCategoryId(categoryService.findByName(cateName).getIdcategory());
        service.insert(article);
        mv.addObject("article", service.findByName(article.getTitle()));
        mv.addObject("comment", new Comment());
        mv.setViewName("article/single-post");
        return mv;
    }
}
