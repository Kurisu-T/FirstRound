package com.anyview.yjy.dao;

import com.anyview.yjy.entity.Orders;
import com.anyview.yjy.entity.User;
import com.anyview.yjy.utils.VO.MovieVO;

import java.time.LocalDateTime;

public class test {
    public static void main(String[] args) {
        MovieVO movieVO = new MovieVO();
        movieVO.setEndTime(LocalDateTime.now());
        movieVO.setShowTime(LocalDateTime.now());
        System.out.println(movieVO.toString());
    }
}
