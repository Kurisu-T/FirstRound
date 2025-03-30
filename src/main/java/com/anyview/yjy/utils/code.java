package com.anyview.yjy.utils;

public class code {

    // 账号状态
    public static final Integer USER_NORMAL = 1;
    public static final Integer USER_BAN = 0;

    // 订单状态
    public static final Long ORDER_UNPAID = 0L;   // 未支付
    public static final Long ORDER_PAID = 1L;     // 已支付
    public static final Long ORDER_CANCEL = 2L;   // 已取消
    public static final Long ORDER_REJECT = 3L;   // 已拒绝
    public static final Long ORDER_WAIT = 4L;     // 待处理
    public static final Long ORDER_COMPLETE = 5L; //已完成
}
