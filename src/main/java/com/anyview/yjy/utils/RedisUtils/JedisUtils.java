package com.anyview.yjy.utils.RedisUtils;

import redis.clients.jedis.Jedis;

public class JedisUtils {

    public static Jedis getJedis() {
        return new Jedis("172.25.84.131", 6379);
    }
}
