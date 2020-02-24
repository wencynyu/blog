package com.yuwenxin.blog.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Tools {

    /**
     * 用来注册加密
     * @param password_in
     * @return
     */
    public Map<String, String> encryption(String password_in){
        SecureRandomNumberGenerator secureRandomNumberGenerator = new SecureRandomNumberGenerator();
        String salt = secureRandomNumberGenerator.nextBytes().toHex();
        String password = new Md5Hash(password_in, salt, 2).toString();
        Map<String, String> map = new HashMap<>();
        map.put("salt", salt);
        map.put("password", password);
        return map;
    }

    public Map<String, String> encryption(String password_in, String salt){
        String password = new Md5Hash(password_in, salt, 2).toString();
        Map<String, String> map = new HashMap<>();
        map.put("salt", salt);
        map.put("password", password);
        return map;
    }

    /**
     * 用来添加cookie信息
     */
    private static final int DEFAULT_COOKIE_AGE = 60 * 60 * 24 * 30 * 3;
    private static final String DEFAULT_COOKIE_PATH = "/";

    public static void addCookie(HttpServletResponse response, String name, String value, Integer maxAge, String path, String domain, Boolean secure){
        try {
            name = URLEncoder.encode(name, "UTF-8");
            value = URLEncoder.encode(value, "UTF-8");
            Cookie cookie = new Cookie(name, value);
            if (maxAge != null) {
                cookie.setMaxAge(maxAge);
            }
            if (!path.isEmpty()) {
                cookie.setPath(path);
            }
            if (!domain.isEmpty()) {
                cookie.setDomain(domain);
            }
            if (secure != null) {
                cookie.setSecure(secure);
            }
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static void addCookie(HttpServletResponse response, String name, String value){
        try {
            name = URLEncoder.encode(name, "UTF-8");
            value = URLEncoder.encode(value, "UTF-8");
            Cookie cookie = new Cookie(name, value);
            cookie.setMaxAge(DEFAULT_COOKIE_AGE);
            cookie.setPath(DEFAULT_COOKIE_PATH);
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String getCookie(HttpServletRequest request, String name){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            try {
                name = URLEncoder.encode(name, "UTF-8");
                for (Cookie cookie : cookies) {
                    if (name.equals(cookie.getName())) {
                        return URLDecoder.decode(cookie.getValue(), "UTF-8");
                    }
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        return null;
    }

    public static void removeCookie(HttpServletResponse response, String name, String path, String domain) {
        try {
            name = URLEncoder.encode(name, "UTF-8");
            Cookie cookie = new Cookie(name, null);
            cookie.setMaxAge(0);
            if (!path.isEmpty()) {
                cookie.setPath(path);
            }
            if (!domain.isEmpty()) {
                cookie.setDomain(domain);
            }
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    public static void removeCookie(HttpServletResponse response, String name) {
        try {
            name = URLEncoder.encode(name, "UTF-8");
            Cookie cookie = new Cookie(name, null);
            cookie.setMaxAge(0);
            cookie.setPath(DEFAULT_COOKIE_PATH);
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 用来验证用户登录注册信息是否规范
     */
    public static boolean username_validate(String username, Map<String, String> errors){
        boolean b = true;
        if (null == username || username.trim().equals("")){
            log.warn("用户名为空");
            errors.put("username", "请输入姓名");
            b = false;
        }
        return b;
    }

    public static boolean password_validate(String password, Map<String, String> errors){
        boolean b = true;
        if (null == password || password.trim().equals("")){
            log.warn("密码为空");
            errors.put("password","请输入密码");
            b = false;
        }else if (password.length() > 20 || password.length() < 6){
            log.warn("密码格式错误");
            errors.put("password","请输入6-20位字符");
            b = false;
        }
        return b;
    }

    public static boolean email_validate(String email, Map<String, String> errors){
        boolean b = true;
        if (email == null || email.trim().equals("")){
            log.warn("邮箱为空");
            errors.put("email", "请输入邮箱");
            b = false;
        }else if (!email
                .matches("[a-zA-Z0-9_-]+@[a-zA-Z0-9_]+(\\.[a-zA-Z0-9_]+)+")){
            log.warn("邮箱格式错误");
            errors.put("email","邮箱格式错误");
            b = false;
        }
        return b;
    }

    public static boolean telephone_validate(String teleNum, Map<String, String> errors){
        boolean b = true;
        if (teleNum == null || teleNum.trim().equals("")){
            log.warn("手机号码为空");
            errors.put("telephone","请输入号码");
            b = false;
        }else if (teleNum.matches("^((13[4-9])|(14[7-8])|(15[0-2,7-9])|(165)|(178)|(18[2-4," +
                "7-8])|(19[5,7,8]))\\d{8}|(170[3,5,6])\\d{7}$")){
            log.info("这是一个移动号码");
        }else if (teleNum.matches("^((13[0-2])|(14[5,6])|(15[5-6])|(16[6-7])|(17[1,5,6])|" +
                "(18[5,6])|(196))\\d{8}|(170[4,7-9])\\d{7}$")){
            log.info("这是一个联通号码");
        }else if (teleNum.matches("^((133)|(149)|(153)|(162)|(17[3,7])|(18[0,1,9])|(19" +
                "[0,1,3,9]))\\d{8}|((170[0-2])|(174[0-5]))\\d{7}$")){
            log.info("这是一个电信号码");
        } else if (teleNum.matches("^((192))\\d{8}$")) {
            log.info("这是一个广电号码");
        }else {
            b = false;
        }
        return b;
    }

    public static boolean login_validate(String username, String password, Map<String, String> errors){
        boolean flag = true;
        if (!username_validate(username,errors)){
            flag = false;
        }
        if (!password_validate(password,errors)){
            flag = false;
        }
        return flag;
    }

    public static boolean register_validate(String username,String password,String passwordConfirm, String email, String sex, String teleNum, Map<String, String> errors){
        boolean flag = login_validate(username, password, errors);

        if (password != null && !password.equals(passwordConfirm)){
            log.warn("确认密码失败");
            errors.put("passwordConfirm","确认密码失败");
            flag = false;
        }

        if (!email_validate(email,errors)){
            flag = false;
        }

        if (sex == null || sex.trim().equals("")){
            log.warn("性别为空");
            errors.put("sex","请选择你的性别");
            flag = false;
        }

        if (!telephone_validate(teleNum,errors)){
            log.warn("手机号码格式错误");
            errors.put("phoneNumber","手机号码格式错误");
            flag = false;
        }

        return flag;
    }
}
