package com.yuwenxin.blog.dao;

import com.yuwenxin.blog.core.BaseDao;
import com.yuwenxin.blog.model.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao extends BaseDao<Comment> {

    /**
     * 查询方法
     */
    @Select("select * from tb_comment")
    List<Comment> findAll();
    @Select("select * from tb_comment where content like concat('%',#{fuzzyname},'%')")
    List<Comment> fuzzyFind(@Param("fuzzyName") String fuzzyname);
    @Select("select * from tb_comment where idcomment>#{bias} limit #{start},#{pageNum}")
    List<Comment> findAllByPage(@Param("bias") Integer bias, @Param("start") Integer start, @Param("pageNum") Integer pageNum);
    @Select("select * from tb_comment where content like concat('%',#{fuzzyname},'%') and idcomment>#{bias} limit #{start},#{pageNum}")
    List<Comment> fuzzyFindByPage(String fuzzyname, Integer bias, Integer start, Integer pageNum);

    @Select("select * from tb_comment where idcomment = #{id}")
    Comment findById(@Param("id") Integer id);
    @Select("select * from tb_comment where content = #{name}")
    Comment findByName(@Param("name") String name);


    @Delete("delete from tb_comment where idcomment = #{id}")
    int deleteById(@Param("id") Integer id);

    int update(Comment comment);
    int insert(Comment comment);

    @Select("select count(*) from tb_comment")
    int getCount();
}
