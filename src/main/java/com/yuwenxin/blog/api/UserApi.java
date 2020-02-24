package com.yuwenxin.blog.api;


import com.yuwenxin.blog.service.implement.UserServiceImpl;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserApi {
    @Autowired
    private UserServiceImpl service;

    @RequestMapping(value = "get={id}")
    @RequiresPermissions("query")
    ResponseEntity<Object> getUserById(@PathVariable("id") String id){
        return new ResponseEntity<>(service.findById(Integer.parseInt(id)), HttpStatus.OK);
    }
}
