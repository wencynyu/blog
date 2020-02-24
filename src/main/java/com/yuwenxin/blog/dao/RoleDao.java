package com.yuwenxin.blog.dao;

import com.yuwenxin.blog.core.BaseDao;
import com.yuwenxin.blog.model.security.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface RoleDao extends BaseDao<Role> {
    /**
     * 查询方法
     */
    @Select("select * from tb_role")
    List<Role> findAll();
    @Select("select * from tb_role where rolename like concat('%',#{fuzzyname},'%')")
    List<Role> fuzzyFind(@Param("fuzzyName") String fuzzyname);
    @Select("select * from tb_role where idrole>#{bias} limit #{start},#{pageNum}")
    List<Role> findAllByPage(@Param("bias") Integer bias, @Param("start") Integer start, @Param("pageNum") Integer pageNum);
    @Select("select * from tb_role where rolename like concat('%',#{fuzzyname},'%') and idrole>#{bias} limit #{start},#{pageNum}")
    List<Role> fuzzyFindByPage(String fuzzyname, Integer bias, Integer start, Integer pageNum);

    @Select("select * from tb_role where idrole = #{id}")
    Role findById(@Param("id") Integer id);
    @Select("select * from tb_role where rolename = #{name}")
    Role findByName(@Param("name") String name);


    @Delete("delete from tb_role where idrole = #{id}")
    int deleteById(@Param("id") Integer id);

    int update(Role role);
    int insert(Role role);

    @Select("select count(*) from tb_role")
    int getCount();

    @Select("select roles.* from usertorole user_role left join tb_role roles on roles.idrole=user_role.idrole where user_role.iduser=#{iduser}")
    Set<Role> findRolesByUserid(@Param("iduser") Integer iduser);
}
