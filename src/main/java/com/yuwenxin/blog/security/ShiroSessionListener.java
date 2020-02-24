package com.yuwenxin.blog.security;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import java.util.concurrent.atomic.AtomicInteger;

public class ShiroSessionListener implements SessionListener {
    /**
     * 这个shiro监听器用来根据session统计在线人数
     */

    private final AtomicInteger sessionCount = new AtomicInteger(0);

    /**
     * 会话创建
     */
    @Override
    public void onStart(Session session) {
        //会话创建，在线人数加一
        sessionCount.incrementAndGet();
    }

    /**
     * 退出会话
     */
    @Override
    public void onStop(Session session) {
        //会话退出,在线人数减一
        sessionCount.decrementAndGet();
    }

    /**
     * 会话过期
     */
    @Override
    public void onExpiration(Session session) {
        //会话过期,在线人数减一
        sessionCount.decrementAndGet();
    }
    /**
     * 获取在线人数
     */
    public AtomicInteger getSessionCount() {
        return sessionCount;
    }

}
