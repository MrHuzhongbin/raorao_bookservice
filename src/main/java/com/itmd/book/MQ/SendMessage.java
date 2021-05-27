package com.itmd.book.MQ;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class SendMessage {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMQ(Long id,String type){
        try {
            amqpTemplate.convertAndSend("book."+type, id);
        } catch (Exception e) {
            log.info("消息发送异常，ID为"+id+"执行的操作是："+type);
        }
    }
}
