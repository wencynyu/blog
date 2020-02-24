package com.yuwenxin.blog.core;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BaseService<T> {
    List<T> findAll();
    List<T> fuzzyFind(String fuzzyname);
    List<T> fuzzyFindByPage(String fuzzyname,Integer bias,Integer start,Integer pageNum);
    List<T> fuzzyFindByPage(String fuzzyname,Integer page);
    List<T> findAllByPage(Integer bias,Integer start,Integer pageNum);
    List<T> findAllByPage(Integer page);
    T findById(Integer idT);
    T findByName(String Tname);
    int insert(T T);
    int deleteById(Integer idT);
    int update(T T);
    int getCount();

    PageUtil<T> findAllByPageUtil(Integer page);
}
