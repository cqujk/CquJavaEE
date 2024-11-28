package org.web2.dao;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisConfig {
    private static final String REDIS_HOST = "localhost";
    private static final int REDIS_PORT = 6379;
    private static final int DATABASE_WEB2 = 2; // 第一个Web应用对应的Redis库
    private static final JedisPool pool;

    static {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(100);
        poolConfig.setMaxIdle(50);
        poolConfig.setMinIdle(10);
        poolConfig.setTestOnBorrow(true);
        pool = new JedisPool(poolConfig, REDIS_HOST, REDIS_PORT, 2000, null, DATABASE_WEB2);
        // 注册 shutdown 钩子
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            clearRedisDatabase(pool);
        }));
    }

    public static JedisPool getPool() {
        return pool;
    }
    private static void clearRedisDatabase(JedisPool pool) {
        try (Jedis jedis = pool.getResource()) {
            jedis.flushDB(); // 清空当前数据库
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}