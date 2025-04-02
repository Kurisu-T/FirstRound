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

    /**
     * 更新订单状态
     * @param order
     */
    public void update(Orders order) {
        orderDao.update(order);
    }

    /**
     * 获取即将放映的电影数量
     * @param userId
     * @return
     */
    public Integer getMovieShow(Long userId) {
        return orderDao.getMovieShow(userId);
    }

    /**
     * 获取申请退款的订单
     * @return
     */
    public List<Orders> getCancelApply() {
        return orderDao.getCancelApply();
    }

    /**
     * 电影结束，更新订单状态
     */
    public void finishTicket() {
        orderDao.finishTicket();
    }
}
