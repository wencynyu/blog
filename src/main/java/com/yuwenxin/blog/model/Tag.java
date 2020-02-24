package com.yuwenxin.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag implements Serializable {

    private Integer idtag;
    private String tagName;

    private static final long serialVersionUID = 1L;

    /**
     * 可以基于tag来构建一个文章推荐系统
     */
}
