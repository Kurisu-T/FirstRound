package com.anyview.yjy.dao;

import com.anyview.yjy.entity.Admin;
import com.anyview.yjy.entity.Movie;
import com.anyview.yjy.entity.Orders;
import com.anyview.yjy.entity.User;
import com.anyview.yjy.utils.VO.*;
import com.anyview.yjy.utils.result.MyResult;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.LocalDateTime;

public class Test {

    public static void main(String[] args) throws JsonProcessingException {
        MovieVO movieVO = new MovieVO();
        User user = new User();
        Orders orders = new Orders();
        Movie movie = new Movie();
        Admin admin = new Admin();
        movie.setShowTime(LocalDateTime.now());
        movie.setCreateTime(LocalDateTime.now());
        movieVO.setShowTime(LocalDateTime.now());
        orders.setShowTime(LocalDateTime.now());
        orders.setCreateTime(LocalDateTime.now());
//        System.out.println(user.toString());
//        System.out.println(admin.toString());
        System.out.println(MyResult.success(movieVO.toString()));
//        System.out.println(MyResult.success(orders.toString()));
//        System.out.println(MyResult.success(movie));

//        AdminLoginVO adminLoginVO = new AdminLoginVO();
//        AdminRegisterVO adminRegisterVO = new AdminRegisterVO();
//        UserLoginVO userLoginVO = new UserLoginVO();
//        UserRegisterVO userRegisterVO = new UserRegisterVO();
//
//        System.out.println(adminLoginVO);
//        System.out.println(adminRegisterVO);
//        System.out.println(userLoginVO);
//        System.out.println(userRegisterVO);

    }
}
