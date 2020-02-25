package com.yuwenxin.blog.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.yuwenxin.blog.security.LoginRealm;
import com.yuwenxin.blog.security.ShiroSessionListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.*;


/**
 * 这里进行Apache-Shiro安全管理框架配置，免去自己手动实现权限管理，session管理，cookie管理，详细信息建议查阅官方文档
 */
@Slf4j
@Configuration
public class ShiroConfig {

    @Bean(name="shiroDialect")
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

    @Bean("sessionListener")
    public ShiroSessionListener sessionListener(){
        return new ShiroSessionListener();
    }

    @Bean
    public SessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }

    /**
     * SessionDAO的作用是为Session提供CRUD并进行持久化的一个shiro组件
     * EnterpriseCacheSessionDAO  提供了缓存功能的会话维护，默认情况下使用MapCache实现，内部使用ConcurrentHashMap保存缓存的会话。
     */
    @Bean
    public SessionDAO sessionDAO() {
        EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
        //sessionId生成器
        enterpriseCacheSessionDAO.setSessionIdGenerator(sessionIdGenerator());
        return enterpriseCacheSessionDAO;
    }

    @Bean("sessionIdCookie")
    public SimpleCookie sessionIdCookie(){
        //这个参数是cookie的名称
        SimpleCookie simpleCookie = new SimpleCookie("sid");
        //设为true后，只能通过http访问，javascript无法访问
        simpleCookie.setHttpOnly(true);
        //maxAge=-1表示浏览器关闭时失效此Cookie
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }

    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        Collection<SessionListener> listeners = new ArrayList<SessionListener>();
        //配置监听
        listeners.add(sessionListener());
        sessionManager.setSessionListeners(listeners);
        sessionManager.setSessionIdCookie(sessionIdCookie());
        sessionManager.setSessionDAO(sessionDAO());
        return sessionManager;

    }

    @Bean("rememberMeCookie")
    public SimpleCookie simpleCookie(){
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setHttpOnly(true);  // 设置js无法访问
        simpleCookie.setMaxAge(60 * 60 * 24 * 15);  // 自动登录生效时间 15 天
        return simpleCookie;
    }

    @Bean
    public RememberMeManager rememberMeManager(){
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        byte[] cipherKey = Base64.decode("wGiHplamyXlVB11UXWol8g==");
        rememberMeManager.setCipherKey(cipherKey);
        rememberMeManager.setCookie(simpleCookie());
        return rememberMeManager;
    }

    // 配置加密方式（md5散列加密两次）
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    //将自己的验证方式加入容器
    @Bean
    public LoginRealm loginRealm() {
        LoginRealm loginRealm =  new LoginRealm();
        loginRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return loginRealm;
    }

    //安全管理，主要是配置Realm的管理认证
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(loginRealm());
        securityManager.setRememberMeManager(rememberMeManager());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filters = new HashMap<>();
        shiroFilterFactoryBean.setFilters(filters);
        Map<String, String> map = new LinkedHashMap<>();
        // 不需要认证的页面
        // 静态资源
        map.put("/static/**", "anon");
        map.put("/assets/**", "anon");
        map.put("/css/**", "anon");
        map.put("/js/**", "anon");
        map.put("/fonts/**", "anon");
        map.put("/images/**", "anon");
        map.put("/common/**", "anon");
        map.put("/plugins/**", "anon");
        map.put("/markdown/**", "anon");
        map.put("/editor**", "anon");

        // 一些简单的内容页面，登录注册页面
        map.put("/404", "anon");
        map.put("/403", "anon");
        map.put("/noAuth", "anon");
        map.put("/index", "anon");
        map.put("/aboutMe", "anon");
        map.put("/register", "anon");
        map.put("/user/doLogin", "anon");
        map.put("/user/doRegister", "anon");
        map.put("/article/view/**", "anon");

        map.put("/validateCode", "anon");

        // 登出页面
        map.put("/logout", "logout");

        // 对所有用户认证
        map.put("/**", "authc");

        // 登录过滤器
        shiroFilterFactoryBean.setLoginUrl("/login");  // 设定静态页面路径
        // 首页 ==> 这里我选择在controller层执行登录操作，便于理解程序
        //shiroFilterFactoryBean.setSuccessUrl("/index");
        // 错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
