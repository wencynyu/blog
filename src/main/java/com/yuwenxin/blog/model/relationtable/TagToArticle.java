package com.yuwenxin.blog.model.relationtable;

import lombok.Data;

@Data
public class TagToArticle {
    /**
     * 标签对文章的中间表 ==》 关联查询
     * 用来：
     * 1.实现根据标签查找感兴趣的文章
     * 2.在文章页面展示相关的标签，便于用户查看
     */
    private Integer idtag;
    private Integer idarticle;
}
