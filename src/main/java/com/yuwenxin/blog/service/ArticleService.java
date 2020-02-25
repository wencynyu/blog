package com.yuwenxin.blog.service;

import com.yuwenxin.blog.core.BaseService;
import com.yuwenxin.blog.core.PageUtil;
import com.yuwenxin.blog.model.Article;
import com.yuwenxin.blog.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface ArticleService extends BaseService<Article> {
    Article findPopularestByCategoryName(String cateName);  // 用于在主页放置各类别最受欢迎的文章
    List<Article> findPopularestArticles(Integer num);  // 用于在主页放置全部文章中最受欢迎的文章
    PageUtil<Article> findAllByPageUtilImpl(Integer pageNum);  // 用于在查询全部文章页面分页查询
    PageUtil<Article> findAllByCategoryAndPageUtil(String catName,Integer page);  // 用于在查找某一类别的全部文章页面分页查询
    PageUtil<Article> fuzzyFindByContentAndPageUtil(String searchName,Integer page);  // 用于全局的模糊搜索（根据文章内容）分页查询
}
