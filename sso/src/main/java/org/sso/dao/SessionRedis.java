package org.sso.dao;

import redis.clients.jedis.Jedis;

public class SessionRedis {
    private static final Jedis jedis = new Jedis("localhost");

    public static void setSession(String appPrefix, String token, String sessionId) {
        jedis.set(appPrefix + ":token:" + token, sessionId);
    }

    public static String getSession(String appPrefix, String token) {
        return jedis.get(appPrefix + ":token:" + token);
    }

    public static void removeSession(String appPrefix, String token) {
        jedis.del(appPrefix + ":token:" + token);
    }

}
