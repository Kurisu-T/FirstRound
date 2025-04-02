package com.anyview.yjy.utils.RedisUtils;

import com.anyview.yjy.entity.Movie;
import com.anyview.yjy.utils.TimeUtils.TimeJSON;
import redis.clients.jedis.Jedis;

import static com.anyview.yjy.utils.code.SELECT_MOVIE;

/**
 * 从 Redis 中获取数据
 */
public class GetDataFormJedis {
    private static Jedis jedis = JedisUtils.getJedis();

    /**
     * 获取 Movie 数据
     * @param movieId
     * @return
     */
    public static final Movie getMovie(Long movieId) {
        Movie movie = new Movie();
        String key = SELECT_MOVIE + movieId;

        if(jedis.hget(key, "id").equals("0")) {
            return null;
        }

        String name = jedis.hget(key, "name");
        String hall = jedis.hget(key, "hall");
        String price = jedis.hget(key, "price");
        String amount = jedis.hget(key, "amount");
        String description = jedis.hget(key, "description");
        String showTime = jedis.hget(key, "showTime");
        String endTime = jedis.hget(key, "endTime");
        String createTime = jedis.hget(key, "createTime");

        movie.setId(movieId);
        movie.setName(name);
        movie.setHall(Long.parseLong(hall));
        movie.setPrice(Integer.parseInt(price));
        movie.setAmount(Integer.parseInt(amount));
        movie.setDescription(description);
        movie.setShowTime(TimeJSON.JSONtoTime(showTime));
        movie.setEndTime(TimeJSON.JSONtoTime(endTime));
        movie.setCreateTime(TimeJSON.JSONtoTime(createTime));

        return movie;
    }
}
