package com.yuwenxin.blog.dao;

import com.yuwenxin.blog.core.BaseDao;
import com.yuwenxin.blog.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends BaseDao<User> {
    /**
     * 查询方法
     */
    @Select("select * from tb_user")
    List<User> findAll();
    @Select("select * from tb_user where username like concat('%',#{fuzzyname},'%')")
    List<User> fuzzyFind(@Param("fuzzyName") String fuzzyname);
    @Select("select * from tb_user where iduser>#{bias} limit #{start},#{pageNum}")
    List<User> findAllByPage(@Param("bias") Integer bias, @Param("start") Integer start, @Param("pageNum") Integer pageNum);
    @Select("select * from tb_user where username like concat('%',#{fuzzyname},'%') and iduser>#{bias} limit #{start},#{pageNum}")
    List<User> fuzzyFindByPage(String fuzzyname, Integer bias, Integer start, Integer pageNum);

    @Select("select * from tb_user where iduser = #{id}")
    User findById(@Param("id") Integer id);
    @Select("select * from tb_user where username = #{name}")
    User findByName(@Param("name") String name);


    @Delete("delete from tb_user where iduser = #{id}")
    int deleteById(@Param("id") Integer id);

    int update(User user);
    int insert(User user);

    @Select("select count(*) from tb_user")
    int getCount();
}
