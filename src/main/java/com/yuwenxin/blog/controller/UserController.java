package com.yuwenxin.blog.controller;


import com.yuwenxin.blog.core.Tools;
import com.yuwenxin.blog.model.User;
import com.yuwenxin.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    private Map<String, String> errors = new HashMap<>();

    @RequestMapping("/doLogin")
    @ExceptionHandler
    public ModelAndView doLogin(@ModelAttribute("user")User user, HttpServletRequest request){
        errors.clear();
        ModelAndView mv = new ModelAndView();
        log.info(user.toString());
        Subject subject = SecurityUtils.getSubject();  // shiro框架的主体部分，即抽象的用户
        Session session = subject.getSession();

        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser != null){
            log.warn(user.getUsername() + "用户已登录");
            errors.put("user","请不要重复登录");
            mv.addObject("errors", errors);
            mv.setViewName("index");
            return mv;
        }

        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
        if (request.getAttribute("remenberMe") == "on"){
            token.setRememberMe(true);
        }

        try {
            subject.login(token);  // 在这里将登录操作交由shiro实现 ==》 调用自定义的Realm操作

            //登录成功以后，更新user的登录时间，并且在session中添加当前user信息
            User activeUser = service.findByName(user.getUsername());
            activeUser.setLatestLoginDate(new Date());
            service.update(activeUser);
            //把用户信息保存到session
            session.setAttribute("user", activeUser);
            mv.setViewName("redirect:/index");
            log.info(user.getUsername() + " 登录成功 " + new Date());
            return mv;
        }catch (AccountException e){
            log.info("用户不存在");
            errors.put("username","用户不存在");
            mv.addObject("errors",errors);
            mv.setViewName("user/login");
        }catch (IncorrectCredentialsException e){
            log.info("密码错误");
            errors.put("password","密码错误");
            mv.addObject("errors",errors);
            mv.setViewName("user/login");
        }catch (AuthenticationException e){
            e.printStackTrace();
            errors.put("unknownErr", e.toString());
            mv.addObject("errors", errors);
            mv.setViewName("user/login");
        }
        log.info("shiro login fail ------ running doLogin()");
        return mv;
    }

    @RequestMapping("/doRegister")
    public ModelAndView doRegister(@ModelAttribute(value = "user")User user){
        errors.clear();
        ModelAndView mv = new ModelAndView();

        // 用户直接访问/user/doRegister页面则重定向至/user/register.html页面
        if (user.equals(new User())){
            mv.setViewName("user/register");
            return mv;
        }

        // 用户已经存在
        User user_ = service.findByName(user.getUsername());
        if (user_ != null){
            log.info("account has been registered.");
            errors.put("username","用户已注册");
            mv.addObject("errors",errors);
            mv.setViewName("user/register");
            return mv;
        }

        // 验证注册信息
        if (!Tools.register_validate(user.getUsername(), user.getPassword(), user.getPasswordConfirm(),
                user.getEmail(), user.getGender().getGender(), user.getPhoneNumber(), errors)){
            mv.addObject("errors",errors);
            mv.setViewName("user/register");
            return mv;
        }

        user.setRegisterTime(new Date());
        service.register(user);
        mv.addObject(user);
        mv.setViewName("user/login");
        return mv;
    }

    @RequestMapping("/doChangePassword")
    public ModelAndView doChangePassword(@ModelAttribute("oldPassword") String oldPassword,@ModelAttribute("newPassword") String newPassword){
        errors.clear();
        ModelAndView mv = new ModelAndView();
        if (!Tools.password_validate(oldPassword, errors)){
            errors.put("oldPassword","旧密码格式错误");
        }

        if (!Tools.password_validate(newPassword, errors)){
            errors.put("newPassword","新密码格式错误");
        }

        if (oldPassword.equals(newPassword)){
            errors.put("password","新旧密码重复");
        }

        // 获取登录后保存在session中的user信息
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");

        if (!user.getPassword().equals(oldPassword)){
            errors.put("password","旧密码错误");
            mv.addObject("errors", errors);
            mv.setViewName("user/change-password");
            return mv;
        }

        // 验证改密信息成功后更新用户
        Tools tools = new Tools();
        Map<String,String> encryptPassword = tools.encryption(newPassword,user.getSalt());
        user.setPassword(encryptPassword.get("password"));
        service.update(user);
        log.info(user.getUsername() + "修改密码成功" + new Date());

        // 修改密码后清除当前session并且需要重新登录
        SecurityUtils.getSubject().getSession().setTimeout(0);

        mv.setViewName("redirect:/index");
        return mv;
    }
}
