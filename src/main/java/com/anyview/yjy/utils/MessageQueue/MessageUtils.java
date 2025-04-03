package com.anyview.yjy.utils.MessageQueue;

import com.anyview.yjy.entity.Message;
import com.anyview.yjy.utils.TimeUtils.TimeJSON;

public class MessageUtils {

    public static Message ParseMessage(String message){
        Message msg = new Message();
        String[] s = message.split("_");
        msg.setTime(TimeJSON.JSONtoTime(s[0]));
        msg.setOrderId(Long.parseLong(s[1]));
        return msg;
    }
}
