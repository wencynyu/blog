package com.yuwenxin.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 一些基本属性：
     * id（主键），标题，文章内容，发表时间
     */
    private Integer idarticle;
    private String title;
    private String content;
    private Integer watchedNum,likeNum;

    private Date postTime;

    /**
     * 文章与类别的多对一关系，通过外键关联
     */
    private Integer belongedCategoryId;
    private Category belongedCategory;

    /**
     * 这里进行深度思考后删除
     * 因为分析需求后明确了系统为私人的专属博客系统，只有博客网站创建者才有发博客的权利
     * 因此所有article的poster都只会是创建者，不需要额外添加字段来保存发表者这个对象
     */
//    private Integer belongedPosterId;
//    private User poster;

    private Set<Tag> tags;
    private List<Comment> comments;
}
