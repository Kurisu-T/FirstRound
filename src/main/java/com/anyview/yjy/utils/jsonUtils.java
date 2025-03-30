package com.anyview.yjy.utils;

import com.anyview.yjy.entity.Movie;
import com.anyview.yjy.entity.Orders;
import com.anyview.yjy.utils.VO.MovieVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;

public class jsonUtils {

    /**
     * 数据转 json
     * @param data
     * @return
     */
    public static String toJson(Object data) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            System.out.println("jsonUtils: toJson error");
            e.printStackTrace();
        }
        return "{\"code\":\"" + 1048596 + "\",\"msg\":\"" + "json 转化失败" + "\"}";
    }

    /**
     * movieVO 转 JSON
     * @param movieVO
     * @return
     */
    public static String toJson(List<MovieVO>movieVO) {
        Integer number = movieVO.size();
        String json = "{\"number\": \"" + number + "\" , \"data\":[";
        for (MovieVO vo : movieVO) {
            json +=  "{";
            Long id = vo.getId();
            String name = vo.getName();
            LocalDateTime showTime = vo.getShowTime();
            Long hall = vo.getHall();
            String description = vo.getDescription();
            json += "\"id\": \"" + id + "\" ,";
            json += "\"name\": \"" + name + "\" ,";
            json += "\"show_time\": \"" + showTime + "\" ,";
            json += "\"hall\": \"" + hall + "\" ,";
            json += "\"description\": \"" + description + "\"";
            json += "}";
            if(vo != movieVO.get(number - 1)) {
                json += ", ";
            }
            else {
                json += "]";
            }
        }
        json += "}";
        return json;
    }

    public static String toMovieJson(List<Movie>list) {
        Integer number = list.size();
        String json = "{\"number\": \"" + list.size() + "\", \"data\": [";

        for (Movie movie : list) {
            json +=  "{";

            Long id = movie.getId();
            String name = movie.getName();
            Long hall = movie.getHall();
            LocalDateTime show_time = movie.getShowTime();
            LocalDateTime createTime = movie.getCreateTime();
            String description = movie.getDescription();
            json += "\"id\": \"" + id + "\", \"name\": \"" + name + "\", ";
            json += "\"hall\": \"" + hall + "\", ";
            json += "\"show_time\": \"" + show_time + "\", ";
            json += "\"create_time\": \"" + createTime + "\", ";
            json += "\"description\": \"" + description + "\"";
            json += "}";

            if(movie != list.get(number - 1)) {
                json += ", ";
            }
            else {
                json += "]";
            }
        }
        json += "}";
        return json;
    }

    public static String toOrderJson(List<Orders> list) {
        Integer number = list.size();
        String json = "{\"number\": \"" + number + "\", \"data\": [";
        for (Orders order : list) {
            json +=  "{";

            Long id = order.getId();
            Long user_id = order.getUserId() == null ? -1L : order.getUserId();
            Long movie = order.getMovie();
            Long hall = order.getHall();
            Long seat = order.getSeat();
            LocalDateTime showTime = order.getShowTime();
            LocalDateTime createTime = order.getCreateTime();
            Long status = order.getStatus();

            json += "\"id\": \"" + id + "\", ";
            if(user_id != -1) {
                json += "\"user_id\": \"" + user_id + "\", ";
            }
            json += "\"movie\": \"" + movie + "\", ";
            json += "\"hall\": \"" + hall + "\", ";
            json += "\"seat\": \"" + seat + "\", ";
            json += "\"show_time\": \"" + showTime + "\", ";
            json += "\"create_time\": \"" + createTime + "\", ";
            json += "\"status\": \"" + status + "\"";
            json += "}";
            if(order != list.get(number - 1)) {
                json += ", ";
            }
            else {
                json += "]";
            }
        }
        json += "}";
        return json;
    }
}
