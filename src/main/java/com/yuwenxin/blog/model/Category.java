package com.yuwenxin.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 一些基本属性：
     * id（主键），类别名称
     */
    private Integer idcategory;
    private String categoryName;

    private List<Article> articleList;
}
