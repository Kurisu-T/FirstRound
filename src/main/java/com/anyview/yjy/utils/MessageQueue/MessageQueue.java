package com.anyview.yjy.utils.MessageQueue;

import com.anyview.yjy.entity.Message;
import com.anyview.yjy.utils.RedisUtils.JedisUtils;
import com.anyview.yjy.utils.TimeUtils.TimeJSON;
import redis.clients.jedis.Jedis;

import java.time.LocalDateTime;

import static com.anyview.yjy.utils.code.ORDER_MESSAGE;
import static com.anyview.yjy.utils.code.ORDER_TTL;


/**
 * 消息队列
 */
public class MessageQueue {

    /**
     * 添加消息
     * @param orderId
     */
    public static void push(Long orderId) {
        Jedis jedis = JedisUtils.getJedis();
        // 在订单的创建时间的基础上添加支付时间，方便后期判断是否支付超市
        LocalDateTime time = LocalDateTime.now().plusMinutes(ORDER_TTL);
        // value 为 "支付超时时间_订单id"
        jedis.lpush(ORDER_MESSAGE, TimeJSON.timeToJSON(time) + "_" + orderId.toString());
    }

    /**
     * 获取消息，满足超时条件则修改订单状态，不满足则 push 回队列中
     * @return
     */
    public static Message pop(){
        Jedis jedis = JedisUtils.getJedis();
        String elem = jedis.rpop(ORDER_MESSAGE);
        if(elem == null){
            return null;
        }
        Message msg = MessageUtils.ParseMessage(elem);
        if(msg.getTime().isBefore(LocalDateTime.now()) ){
            return  msg;
        } else {
            // 未超时，重新 push 到队列中
            jedis.rpush(ORDER_MESSAGE, elem);
        }
        return null;
    }

}
