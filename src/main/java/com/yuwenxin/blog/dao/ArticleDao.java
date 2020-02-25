package com.yuwenxin.blog.dao;

import com.yuwenxin.blog.core.BaseDao;
import com.yuwenxin.blog.model.Article;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleDao extends BaseDao<Article> {

    /**
     * 查询方法
     */
    @Select("select * from tb_article")
    List<Article> findAll();
    @Select("select * from tb_article where content like concat('%',#{fuzzyname},'%')")
    List<Article> fuzzyFind(@Param("fuzzyname") String fuzzyname);
    @Select("select * from tb_article where content like concat('%',#{fuzzyname},'%') and idarticle>#{bias} limit #{start},#{pageNum}")
    List<Article> fuzzyFindByPage(@Param("fuzzyname") String fuzzyname, @Param("bias") Integer bias, @Param("start") Integer start, @Param("pageNum") Integer pageNum);
    @Select("select count(*) from tb_article where content like concat('%',#{fuzzyname},'%')")
    int fuzzyCount(@Param("fuzzyname") String fuzzyName);
    @Select("select * from tb_article where idarticle>#{bias} limit #{start},#{pageNum}")
    List<Article> findAllByPage(@Param("bias") Integer bias, @Param("start") Integer start, @Param("pageNum") Integer pageNum);

    @Select("select * from tb_article where idarticle = #{id}")
    Article findById(@Param("id") Integer id);
    @Select("select * from tb_article where title = #{name}")
    Article findByName(@Param("name") String name);

    @Delete("delete from tb_article where idarticle = #{id}")
    int deleteById(@Param("id") Integer id);

    // 插入和修改方法使用mybatis的传统配置xml来进行动态执行
    int update(Article article);
    int insert(Article article);

    @Select("select count(*) from tb_article")
    int getCount();

    /**
     * 这里是一些专有的查询方法
     * 如：根据内容来模糊查询，查询最受欢迎的文章，根据种类查询最受欢迎文章......
     */
    @Select("select * from tb_article order by watchedNum desc limit #{num}")
    List<Article> findPopularestArticles(@Param("num") Integer num);

    @Select("select * from tb_article where belongedCategoryid = (select idcategory from tb_category where categoryname = #{cateName}) order by watchedNum desc limit 1")
    Article findPopularestByCategoryName(@Param("cateName") String cateName); // 这里最高级没用the most，作为约定，不管英语语法


    @Select("select count(*) from tb_article where belongedCategoryid = (select idcategory from tb_category where categoryname = #{cateName})")
    int getCateCount(@Param("cateName") String cateName);
    @Select("select * from tb_article where belongedCategoryid = (select idcategory from tb_category where categoryname = #{cateName}) and idarticle>#{bias} limit #{start},#{pageNum} ")
    List<Article> findByCategoryAndPage(@Param("cateName")String cateName,@Param("bias") Integer bias, @Param("start") Integer start, @Param("pageNum") Integer pageNum);
}
