package com.anyview.yjy.dao;

import com.anyview.yjy.entity.User;
import com.anyview.yjy.utils.RedisUtils.JedisUtils;
import redis.clients.jedis.Jedis;

// 测试用，无需理会
public class Test {
    private static UserDao userService = new UserDao();

    public static void main(String[] args) {
        Jedis jedis = JedisUtils.getJedis();
        jedis.set("test", "test");
        String s = jedis.get("test");
        System.out.println(s);
        jedis.del("test");

        User user = userService.getById(1L);
        System.out.println(user);
    }
}
