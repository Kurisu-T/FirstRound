package com.anyview.yjy.utils.RedisUtils;

import com.anyview.yjy.entity.Movie;
import com.anyview.yjy.utils.DataUtils.ParseHash;
import redis.clients.jedis.Jedis;

import java.util.Map;

import static com.anyview.yjy.utils.code.TTL;

/**
 * 添加数据到 Redis
 */
public class PutRedis {

    /**
     * 添加 Movie 到 Redis
     * @param jedis
     * @param key
     * @param movie
     * @return
     */
    public static boolean putMovie(Jedis jedis, String key, Movie movie) {
        Map<String, String> map = ParseHash.MovieToHash(movie);
        long res = jedis.hset(key, map);
        if(res == 0) return false;
        jedis.expire(key,TTL);
        return true;
    }
}
