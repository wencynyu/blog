package com.yuwenxin.blog.core;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.yuwenxin.blog.core.Settings.DEFAULT_PAGENUM;

public abstract class BaseServiceImpl<T> implements BaseService<T>{
    @Autowired
    BaseDao<T> baseDao;

    @Override
    public List<T> findAll() {
        return baseDao.findAll();
    }

    @Override
    public List<T> fuzzyFind(String fuzzyname) {
        return baseDao.fuzzyFind(fuzzyname);
    }

    public List<T> fuzzyFindByPage(String fuzzyname,Integer bias,Integer start,Integer pageNum){
        return baseDao.fuzzyFindByPage(fuzzyname, bias, start, pageNum);
    }

    public List<T> fuzzyFindByPage(String fuzzyname,Integer page){
        return fuzzyFindByPage(fuzzyname,Settings.DEFAULT_BIAS,(page-1) * DEFAULT_PAGENUM, DEFAULT_PAGENUM);
    }

    public List<T> findAllByPage(Integer bias, Integer start, Integer pageNum) {
        return baseDao.findAllByPage(bias,start,pageNum);
    }

    public List<T> findAllByPage(Integer page) {
        return findAllByPage(Settings.DEFAULT_BIAS, (page-1)* DEFAULT_PAGENUM, DEFAULT_PAGENUM);
    }

    @Override
    public T findById(Integer idT) {
        return baseDao.findById(idT);
    }

    @Override
    public T findByName(String Tname) {
        return baseDao.findByName(Tname);
    }

    @Override
    public int insert(T t) {
        return baseDao.insert(t);
    }

    @Override
    public int deleteById(Integer idT) {
        return baseDao.deleteById(idT);
    }

    @Override
    public int update(T t) {
        return baseDao.update(t);
    }

    @Override
    public int getCount() {
        return baseDao.getCount();
    }

    @Override
    public PageUtil<T> findAllByPageUtil(Integer page) {
        int count = getCount();
        PageUtil<T> pageUtil = new PageUtil<>(page, count);
        List<T> list = findAllByPage(page);
        pageUtil.setTableList(list);
        return pageUtil;
    }

}
