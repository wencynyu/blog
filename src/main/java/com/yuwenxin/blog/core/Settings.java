package com.yuwenxin.blog.core;

public interface Settings {
    /**
     * 这里用来存储分页查询的默认参数（在service层中实现）
     */
    Integer DEFAULT_BIAS=0;
    Integer DEFAULT_PAGENUM=15;  // 默认的一页存放量
    Integer DEFAULT_POPULAR_NUM=5;
}
