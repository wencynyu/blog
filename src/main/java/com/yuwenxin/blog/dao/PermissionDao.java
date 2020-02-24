package com.yuwenxin.blog.dao;

import com.yuwenxin.blog.core.BaseDao;
import com.yuwenxin.blog.model.security.Permission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface PermissionDao extends BaseDao<Permission> {

    /**
     * 查询方法
     */
    @Select("select * from tb_permission")
    List<Permission> findAll();
    @Select("select * from tb_permission where permissionname like concat('%',#{fuzzyname},'%')")
    List<Permission> fuzzyFind(@Param("fuzzyName") String fuzzyname);
    @Select("select * from tb_permission where idpermission>#{bias} limit #{start},#{pageNum}")
    List<Permission> findAllByPage(@Param("bias") Integer bias, @Param("start") Integer start, @Param("pageNum") Integer pageNum);
    @Select("select * from tb_permission where permissionname like concat('%',#{fuzzyname},'%') and idpermission>#{bias} limit #{start},#{pageNum}")
    List<Permission> fuzzyFindByPage(String fuzzyname, Integer bias, Integer start, Integer pageNum);

    @Select("select * from tb_permission where idpermission = #{id}")
    Permission findById(@Param("id") Integer id);
    @Select("select * from tb_permission where permissionname = #{name}")
    Permission findByName(@Param("name") String name);


    @Delete("delete from tb_permission where idpermission = #{id}")
    int deleteById(@Param("id") Integer id);

    int update(Permission permission);
    int insert(Permission permission);

    @Select("select count(*) from tb_permission")
    int getCount();

    @Select("select perms.* from roletopermission role_perm left join tb_permission perms on perms.idpermission=role_perm.idpermission where role_perm.idrole=#{idrole}")
    Set<Permission> findPermissionByRoleid(@Param("idrole") Integer idrole);
}
