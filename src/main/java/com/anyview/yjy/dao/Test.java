package com.anyview.yjy.dao;

import com.anyview.yjy.entity.Movie;
import com.anyview.yjy.utils.DataUtils.ParseHash;
import com.anyview.yjy.utils.RedisUtils.JedisUtils;
import redis.clients.jedis.Jedis;

import java.time.LocalDateTime;
import java.util.Map;

import static com.anyview.yjy.utils.code.SELECT_MOVIE;

public class Test {
    public static void main(String[] args) {
        Jedis jedis = JedisUtils.getJedis();
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setName("test");
        movie.setHall(1L);
        movie.setPrice(100);
        movie.setAmount(101);
        movie.setDescription("Description");
        movie.setShowTime(LocalDateTime.now().plusHours(1));
        movie.setEndTime(LocalDateTime.now().plusHours(2));
        movie.setCreateTime(LocalDateTime.now());
        Map<String,String>map = ParseHash.MovieToHash(movie);
        jedis.hset(SELECT_MOVIE + 1, map);
    }
}
