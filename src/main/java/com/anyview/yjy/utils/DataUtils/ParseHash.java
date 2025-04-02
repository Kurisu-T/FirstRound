package com.anyview.yjy.utils.DataUtils;

import com.anyview.yjy.entity.Movie;
import com.anyview.yjy.utils.TimeUtils.TimeJSON;

import java.util.HashMap;
import java.util.Map;

public class ParseHash {

    /**
     * 将 Movie 的数据转换成 HashMap 形式并返回, 用于创建 redis 缓存
     * @param movie
     * @return
     */
    public static final Map<String, String> MovieToHash(Movie movie) {

        Map<String,String>map = new HashMap<>();

        map.put("id", movie.getId().toString());
        map.put("name", movie.getName());
        map.put("hall", movie.getHall().toString());
        map.put("price", movie.getPrice().toString());
        map.put("amount", movie.getAmount().toString());
        map.put("showTime", TimeJSON.timeToJSON(movie.getShowTime()));
        map.put("endTime", TimeJSON.timeToJSON(movie.getEndTime()));
        map.put("description", movie.getDescription());
        map.put("createTime", TimeJSON.timeToJSON(movie.getCreateTime()));

        return map;
    }
}
