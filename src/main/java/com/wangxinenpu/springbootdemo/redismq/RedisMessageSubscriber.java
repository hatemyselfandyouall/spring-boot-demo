package com.wangxinenpu.springbootdemo.redismq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author robust
 * @date 2021/6/7
 */
@Slf4j
@Component
public class RedisMessageSubscriber implements MessageListener {
    public static List<String> messageList = new ArrayList<>();

    @Override
    public void onMessage(Message message, byte[] pattern) {
        messageList.add(message.toString());
        log.info("订阅方接收到了消息==>{}", message.toString());
    }
}
