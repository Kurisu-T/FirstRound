package com.anyview.yjy.dao;

import com.anyview.yjy.entity.Orders;
import com.anyview.yjy.entity.User;

import java.time.LocalDateTime;

public class test {
    public static void main(String[] args) {
        Orders order = new Orders();
        order.setShowTime(LocalDateTime.now());
        order.setCreateTime(LocalDateTime.now());
        System.out.println(order.toString());
    }
}
