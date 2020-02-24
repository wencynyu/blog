package com.yuwenxin.blog.service.implement;

import com.yuwenxin.blog.core.BaseServiceImpl;
import com.yuwenxin.blog.core.PageUtil;
import com.yuwenxin.blog.core.Settings;
import com.yuwenxin.blog.dao.*;
import com.yuwenxin.blog.model.Article;
import com.yuwenxin.blog.model.Category;
import com.yuwenxin.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl extends BaseServiceImpl<Article> implements ArticleService {
    @Autowired
    private ArticleDao dao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private TagDao tagDao;
    @Autowired
    private CommentDao commentDao;

    @Override
    public Article findPopularestByCategoryName(String cateName) {
        return dao.findPopularestByCategoryName(cateName);
    }

    @Override
    public List<Article> findPopularestArticles(Integer num) {
        return dao.findPopularestArticles(num);
    }

    @Override
    public PageUtil<Article> findAllByPageUtilInv(Integer pageNum) {
        // findAllByPageUtil
        PageUtil<Article> articlePageUtil = findAllByPageUtil(pageNum);
        List<Article> articles = articlePageUtil.getTableList();
        // WithCategory
        for (Article art :
                articles) {
            Integer catId = art.getBelongedCategoryId();
            art.setBelongedCategory(categoryDao.findById(catId));
        }
        return articlePageUtil;
    }

    public Article findByNameWithPoster(String name){
        Article article = findByName(name);
        article.setPoster(userDao.findById(article.getBelongedPosterId()));
        return article;
    }

    public PageUtil<Article> findAllByCategoryAndPageUtil(String catName,Integer page){
        return null;
    }

    @Override
    public PageUtil<Article> fuzzyFindByContentAndPageUtil(String searchName, Integer page) {
        return null;
    }
}
