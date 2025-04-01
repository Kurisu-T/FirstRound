package com.anyview.yjy.utils.RedisUtils;

import redis.clients.jedis.Jedis;

public class JedisUtils {
    public static Jedis getJedis() {
        Jedis jedis = new Jedis("172.25.84.131", 6379);
        return jedis;
    }
}
