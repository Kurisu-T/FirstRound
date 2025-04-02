package com.anyview.yjy.utils.RedisUtils;

import redis.clients.jedis.Jedis;

public class JedisUtils {

    /**
     * 链接 Redis
     * @return
     */
    public static Jedis getJedis() {
        return new Jedis("172.25.84.131", 6379);
    }
}
