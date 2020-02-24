package com.yuwenxin.blog.core;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseDao<T> {
    List<T> findAll();
    List<T> fuzzyFind(String fuzzyname);
    List<T> findAllByPage(Integer bias,Integer start,Integer pageNum);
    List<T> fuzzyFindByPage(String fuzzyname,Integer bias,Integer start,Integer pageNum);

    T findById(Integer id);
    T findByName(String name);

    int insert(T t);
    int deleteById(Integer id);
    int update(T t);

    int getCount();
}
