package com.yuwenxin.blog.dao;

import com.yuwenxin.blog.core.BaseDao;
import com.yuwenxin.blog.model.Tag;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagDao extends BaseDao<Tag> {
    /**
     * 查询方法
     */
    @Select("select * from tb_tag")
    List<Tag> findAll();
    @Select("select * from tb_tag where tagname like concat('%',#{fuzzyname},'%')")
    List<Tag> fuzzyFind(@Param("fuzzyName") String fuzzyname);
    @Select("select * from tb_tag where idtag>#{bias} limit #{start},#{pageNum}")
    List<Tag> findAllByPage(@Param("bias") Integer bias, @Param("start") Integer start, @Param("pageNum") Integer pageNum);
    @Select("select * from tb_tag where tagname like concat('%',#{fuzzyname},'%') and idtag>#{bias} limit #{start},#{pageNum}")
    List<Tag> fuzzyFindByPage(String fuzzyname, Integer bias, Integer start, Integer pageNum);

    @Select("select * from tb_tag where idtag = #{id}")
    Tag findById(@Param("id") Integer id);
    @Select("select * from tb_tag where tagname = #{name}")
    Tag findByName(@Param("name") String name);


    @Delete("delete from tb_tag where idtag = #{id}")
    int deleteById(@Param("id") Integer id);

    int update(Tag tag);
    int insert(Tag tag);

    @Select("select count(*) from tb_tag")
    int getCount();
}
