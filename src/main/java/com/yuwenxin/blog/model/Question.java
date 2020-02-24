package com.yuwenxin.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 一些基本属性：
     * id（主键），问题内容，发表时间，是否审核通过
     */
    private Integer idquestion;
    private String content;

    private Date postDate;

    private boolean isChecked;

    /**
     * 问题与发表者（用户）的一对一关系，通过question类下的外键关联
     */
    private Integer belongedPosterId;
    private User poster;

    /**
     * todo: 这里没有实现给问题的答案及评论，有实际需求可以根据情况来修改表结构以及添加对应的逻辑实现
     */

}
