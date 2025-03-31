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
    public Integer add(Long userId, Long movieId,Long seatId) {
        return orderDao.add(userId, movieId, seatId);
    }

    /**
     * 获取所有订单信息
     * @return
     */
    public List<Orders> getAll() {
        return orderDao.getAll();
    }

    /**
     * 获取订单详细
     *
     * @param orderId
     * @return
     */
    public Orders getById(Long orderId) {
        return orderDao.getById(orderId);
    }
}
