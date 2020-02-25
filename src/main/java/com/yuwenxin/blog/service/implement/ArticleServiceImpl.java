package com.yuwenxin.blog.service.implement;

import com.yuwenxin.blog.core.BaseServiceImpl;
import com.yuwenxin.blog.core.PageUtil;
import com.yuwenxin.blog.core.Settings;
import com.yuwenxin.blog.dao.*;
import com.yuwenxin.blog.model.Article;
import com.yuwenxin.blog.model.Category;
import com.yuwenxin.blog.service.ArticleService;
import org.omg.CORBA.ARG_IN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yuwenxin.blog.core.Settings.DEFAULT_PAGENUM;

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
    public Article findByName(String Tname) {
        Article article = super.findByName(Tname);
        if (article == null){
            return null;
        }
        article.setBelongedCategory(categoryDao.findById(article.getBelongedCategoryId()));
        article.setComments(commentDao.findCommentByArticleid(article.getIdarticle()));
        return article;
    }

    @Override
    public Article findPopularestByCategoryName(String cateName) {
        return dao.findPopularestByCategoryName(cateName);
    }

    @Override
    public List<Article> findPopularestArticles(Integer num) {
        return dao.findPopularestArticles(num);
    }

    @Override
    public PageUtil<Article> findAllByPageUtilImpl(Integer pageNum) {
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

    @Override
    public PageUtil<Article> findAllByCategoryAndPageUtil(String catName,Integer page){
        int cateCount = dao.getCateCount(catName);
        PageUtil<Article> articlePageUtil = new PageUtil<>(page,cateCount);
        List<Article> articles = dao.findByCategoryAndPage(catName, Settings.DEFAULT_BIAS,(page-1)* DEFAULT_PAGENUM, DEFAULT_PAGENUM);
        for (Article art :
                articles) {
            Integer catId = art.getBelongedCategoryId();
            art.setBelongedCategory(categoryDao.findById(catId));
        }
        articlePageUtil.setTableList(articles);
        return articlePageUtil;
    }

    @Override
    public PageUtil<Article> fuzzyFindByContentAndPageUtil(String searchName, Integer page) {
        int tableCount = dao.fuzzyCount(searchName);
        PageUtil<Article> articlePageUtil = new PageUtil<>(page,tableCount);
        List<Article> articles =  fuzzyFindByPage(searchName,page);
        for (Article art :
                articles) {
            Integer catId = art.getBelongedCategoryId();
            art.setBelongedCategory(categoryDao.findById(catId));
        }
        articlePageUtil.setTableList(articles);
        return articlePageUtil;
    }
}
