package com.anyview.yjy.service;

import com.anyview.yjy.dao.OrderDao;
import com.anyview.yjy.entity.Orders;

import java.util.List;

public class OrderService {
    OrderDao orderDao = new OrderDao();

    /**
     * 获取订单列表
     * @return
     */
    public List<Orders> list(Long userId, Long adminId) {
        return orderDao.list(userId, adminId);
    }

    /**
     * 添加订单
     * @param userId
     * @param movieId
     * @param seatId
     * @return
     */
    public Long add(Long userId, Long movieId,Long seatId) {
        return orderDao.add(userId, movieId, seatId);
    }
}
