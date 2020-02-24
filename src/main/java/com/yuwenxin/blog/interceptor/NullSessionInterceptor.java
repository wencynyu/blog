package com.yuwenxin.blog.interceptor;

import com.yuwenxin.blog.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NullSessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        Session session = SecurityUtils.getSubject().getSession();
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null){
            User user = new User();
            user.setAdmin(false);
            session.setAttribute("user", user);
        }
        return true;
    }
}
