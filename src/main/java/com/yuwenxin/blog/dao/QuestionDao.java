package com.yuwenxin.blog.dao;

import com.yuwenxin.blog.core.BaseDao;
import com.yuwenxin.blog.model.Question;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends BaseDao<Question> {
    /**
     * 查询方法
     */
    @Select("select * from tb_question")
    List<Question> findAll();
    @Select("select * from tb_question where content like concat('%',#{fuzzyname},'%')")
    List<Question> fuzzyFind(@Param("fuzzyname") String fuzzyname);
    @Select("select count(*) from tb_question where content like concat('%',#{fuzzyname},'%')")
    int fuzzyCount(@Param("fuzzyname") String fuzzyName);
    @Select("select * from tb_question where idquestion>#{bias} limit #{start},#{pageNum}")
    List<Question> findAllByPage(@Param("bias") Integer bias, @Param("start") Integer start, @Param("pageNum") Integer pageNum);
    @Select("select * from tb_question where content like concat('%',#{fuzzyname},'%') and idquestion>#{bias} limit #{start},#{pageNum}")
    List<Question> fuzzyFindByPage(String fuzzyname, Integer bias, Integer start, Integer pageNum);

    @Select("select * from tb_question where idquestion = #{id}")
    Question findById(@Param("id") Integer id);
    @Select("select * from tb_question where content = #{name}")
    Question findByName(@Param("name") String name);


    @Delete("delete from tb_question where idquestion = #{id}")
    int deleteById(@Param("id") Integer id);

    int update(Question question);
    int insert(Question question);

    @Select("select count(*) from tb_question")
    int getCount();
}
