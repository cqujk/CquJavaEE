package org.sso.dao;

import redis.clients.jedis.Jedis;

import java.util.Map;

public class UserSessionRedis {

    public void login(String userId, long loginTime) {
        try (Jedis jedis = RedisConfig.getPool().getResource()) {
            jedis.hset("user_sessions", userId, String.valueOf(loginTime));
        }
    }

    public void logout(String userId) {
        try (Jedis jedis = RedisConfig.getPool().getResource()) {
            jedis.hdel("user_sessions", userId);
        }
    }

    public Map<String, String> getAllUsers() {
        try (Jedis jedis = RedisConfig.getPool().getResource()) {
            return jedis.hgetAll("user_sessions");
        }
    }
    public int getCurrentActiveSessions() {
        try (Jedis jedis = RedisConfig.getPool().getResource()) {
            return (int) jedis.hlen("user_sessions");
        }
    }
}
