package org.web1.listener;

import org.web1.dao.UserSessionRedis;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {
    private UserSessionRedis sessionRedis=new UserSessionRedis();
    @Override
    public void sessionCreated(HttpSessionEvent event)
    {
        sessionRedis.login((String) event.getSession().getAttribute("userId"),System.currentTimeMillis());
//        String sessionId=event.getSession().getId();
//        sessionRedis.addTokenMapping(sessionId,sessionId,30*60);
    }
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        sessionRedis.logout((String) se.getSession().getAttribute("userId"));
    }
    public int getActiveSessions() {
        return sessionRedis.getCurrentActiveSessions();
    }
}
