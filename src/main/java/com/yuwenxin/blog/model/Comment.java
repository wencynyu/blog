package com.yuwenxin.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private static final long serialVersionUID = 1L;

    /**
     * 一些基本属性：
     * id（主键），内容，发表时间
     */
    private Integer idcomment;
    private String content;

    private Date postDate;

    /**
     * 评论与发表者（用户）的一对一关系，通过comment类下的外键关联  ==》  实际上一对一关系关联可以在任意一方增添外键
     */
    private Integer belongedPosterId;
    private User Poster;
    private Integer belongedArticleId;
    private Article article;
    /**
     * todo: 这里没有实现给评论的评论，有实际需求可以根据情况来修改表结构以及添加对应的逻辑实现
     */
}
