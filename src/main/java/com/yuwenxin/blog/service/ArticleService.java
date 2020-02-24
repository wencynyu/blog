package com.yuwenxin.blog.service;

import com.yuwenxin.blog.core.BaseService;
import com.yuwenxin.blog.core.PageUtil;
import com.yuwenxin.blog.model.Article;
import com.yuwenxin.blog.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface ArticleService extends BaseService<Article> {
    Article findPopularestByCategoryName(String cateName);
    List<Article> findPopularestArticles(Integer num);
    PageUtil<Article> findAllByPageUtilInv(Integer pageNum);
    Article findByNameWithPoster(String name);
    PageUtil<Article> findAllByCategoryAndPageUtil(String catName,Integer page);
    PageUtil<Article> fuzzyFindByContentAndPageUtil(String searchName,Integer page);
}
