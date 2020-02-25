package com.yuwenxin.blog.dao;

import com.yuwenxin.blog.core.BaseDao;
import com.yuwenxin.blog.model.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryDao extends BaseDao<Category> {

    /**
     * 查询方法
     */
    @Select("select * from tb_category")
    List<Category> findAll();
    @Select("select * from tb_category where categoryname like concat('%',#{fuzzyname},'%')")
    List<Category> fuzzyFind(@Param("fuzzyname") String fuzzyname);
    @Select("select count(*) from tb_category where categoryname like concat('%',#{fuzzyname},'%')")
    int fuzzyCount(@Param("fuzzyname") String fuzzyName);
    @Select("select * from tb_category where idcategory>#{bias} limit #{start},#{pageNum}")
    List<Category> findAllByPage(@Param("bias") Integer bias, @Param("start") Integer start, @Param("pageNum") Integer pageNum);
    @Select("select * from tb_category where categoryname like concat('%',#{fuzzyname},'%') and idcategory>#{bias} limit #{start},#{pageNum}")
    List<Category> fuzzyFindByPage(String fuzzyname, Integer bias, Integer start, Integer pageNum);

    @Select("select * from tb_category where idcategory = #{id}")
    Category findById(@Param("id") Integer id);
    @Select("select * from tb_category where categoryname = #{name}")
    Category findByName(@Param("name") String name);


    @Delete("delete from tb_category where idcategory = #{id}")
    int deleteById(@Param("id") Integer id);

    int update(Category category);
    int insert(Category category);

    @Select("select count(*) from tb_category")
    int getCount();
}
