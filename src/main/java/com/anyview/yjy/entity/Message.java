package com.anyview.yjy.entity;

import java.time.LocalDateTime;

/**
 * 消息，用于处理一定时间内未支付的订单，
 * 修改订单状态为 ORDER_CANCEL 已取消
 */
public class Message {
    private LocalDateTime time;
    private Long orderId;

    public Message() {
    }

    public Message(LocalDateTime time, Long orderId) {
        this.time = time;
        this.orderId = orderId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String toString() {
        return "Message{time = " + time + ", orderId = " + orderId + "}";
    }
}
