package com.yuwenxin.blog.controller;


import com.yuwenxin.blog.service.PermissionService;
import com.yuwenxin.blog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller("/admin/auth")
public class AuthController {
    /**
     * 这个控制器负责权限管理
     */
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/role/view/{page}")
    public ModelAndView toRoles(@PathVariable("page")Integer page){
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    @RequestMapping("/permission/view/{page}")
    public ModelAndView toPermissions(@PathVariable("page")Integer page){
        ModelAndView mv = new ModelAndView();
        return mv;
    }

}
