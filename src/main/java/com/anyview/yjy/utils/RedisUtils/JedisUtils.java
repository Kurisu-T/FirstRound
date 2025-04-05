package com.anyview.yjy.utils.RedisUtils;

import redis.clients.jedis.Jedis;

import static com.anyview.yjy.utils.code.JEDIS_PASSWORD;

public class JedisUtils {

    /**
     * 链接 Redis
     * @return
     */
    public static Jedis getJedis() {
        Jedis jedis = new Jedis("172.25.84.131", 6379);
        jedis.auth(JEDIS_PASSWORD);
        return jedis;
    }
}
