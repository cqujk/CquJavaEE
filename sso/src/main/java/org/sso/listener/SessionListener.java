package org.sso.listener;

import org.sso.dao.UserSessionRedis;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

public class SessionListener implements HttpSessionListener {
    private UserSessionRedis UserSessionManager=new UserSessionRedis();

    @Override
    public void sessionCreated(HttpSessionEvent event){
        UserSessionManager.login((String) event.getSession().getAttribute("userId"), System.currentTimeMillis());
    }
    @Override
    public void sessionDestroyed(HttpSessionEvent event){
        // 从Redis中移除用户信息
        UserSessionManager.logout((String) event.getSession().getAttribute("userId"));
    }
    public  int getActiveSessions(){
    	return UserSessionManager.getCurrentActiveSessions();
    }
}
