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
        if(number == 0){
            String json = "{\"code\": 500,\"msg\":\"error\",\"data\": null}";
            return json;
        }
        String json = "{\"code\":200,\"msg\":\"success\",\"data\":{\"number\":" + number + ",\"list\":[";
        for (MovieVO vo : movieVO) {
            json += "{\"id\":" + vo.getId() + ", \"name\":\"" + vo.getName() +
                    "\", \"show_time\":\"" + vo.getShowTime() +
                    "\",\"hall\":" + vo.getHall() + ",\"description\":\"" +
                    vo.getDescription() + "\"}";
            if(vo == movieVO.get(movieVO.size()-1)){
                json += "]";
            } else {
                json += ",";
            }
        }
        json += "}}";
        return json;
    }

    public static String toMovieJson(List<Movie>list) {
        Integer number = list.size();
        if(number == 0){
            String json = "{\"code\": 500,\"msg\":\"error\",\"data\": null}";
            return json;
        }
        String json = "{\"code\":200,\"msg\":\"success\",\"data\":{\"number\":" + number + ",\"list\":[";
        for (Movie vo : list) {
            json += "{\"id\":" + vo.getId() + ", \"name\":\"" + vo.getName() +
                    "\", \"show_time\":\"" + vo.getShowTime() +
                    "\", \"create_time\":\"" + vo.getShowTime() +
                    "\",\"hall\":" + vo.getHall() + ",\"description\":\"" +
                    vo.getDescription() + "\"}";
            if(vo == list.get(list.size()-1)){
                json += "]";
            } else {
                json += ",";
            }
        }
        json += "}}";
        return json;
    }

    public static String toOrderJson(List<Orders> list) {
        Integer number = list.size();
        String json = "{\"number\": " + number + ", \"data\": [";
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
