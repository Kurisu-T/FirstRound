package com.anyview.yjy.utils;

public class code {
    // 悲观锁前缀
    public static final String LOCK = "lock:movie:";
    // 电影查询缓存前缀
    public static final String SELECT_MOVIE = "select:movie:";
    // 订单消息队列前缀
    public static final String ORDER_MESSAGE = "order:message";
    // redis 密码
    public static final String JEDIS_PASSWORD = "123";

    // 订单超市时间 秒
    public static final Integer ORDER_TTL = 1;
    // 检查订单状态的时间间隔 分
    public static final Integer SELECT_MOVIE_TIME = 1;
    // 缓存存活时间 秒
    public static final Integer TTL = 10;       // 缓存穿透
    public static final Integer LOCK_TTL = 5;  // 悲观锁

    // 账号状态
    public static final Integer USER_BAN = 0;       // 恢复
    public static final Integer USER_NORMAL = 1;    // 拉黑

    // 订单状态
    public static final Long ORDER_UNPAID = 0L;   // 未支付
    public static final Long ORDER_PAID = 1L;     // 已支付
    public static final Long ORDER_CANCEL = 2L;   // 已取消
    public static final Long ORDER_REJECT = 3L;   // 已拒绝
    public static final Long ORDER_WAIT = 4L;     // 待处理
    public static final Long ORDER_COMPLETE = 5L; // 已完成

    // 购票结果
    public static final Integer  MOVIE_NO_FIND = 0;     // 电影信息未找到
    public static final Integer  SEAT_NOT_NULL = -1;   // 座位已被占用
    public static final Integer  BUY_FAIL = -2;        // 购买失败
    public static final Integer  LACK = -3;            // 库存不足
}
