package com.usian.listener;

import com.rabbitmq.client.Channel;
import com.usian.pojo.DeDuplication;
import com.usian.pojo.LocalMessage;
import com.usian.service.DeDuplicationService;
import com.usian.service.ItemService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OrderIdMQListener {
    @Autowired
    ItemService itemService;

    @Autowired
    DeDuplicationService deDuplicationService;

    @Autowired
    LocalMessage localMessage;
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value="order_queue",durable = "true"),
            exchange = @Exchange(value="orderid",type= ExchangeTypes.TOPIC,durable = "true"),
            key= {"*.*"}
    ))
    public void listen(String msg, Channel channel, Message message) throws IOException {
        //进行幂等判断，防止ack应为网络问题没有送达，导致扣减库存业务重复执行
        DeDuplication deDuplication =
                deDuplicationService.selectDeDuplicationByTxNo(localMessage.getTxNo());
        if (deDuplication == null) {
            int i = itemService.orderDeleteNum(msg);
            if (i < 0) {
                throw new RuntimeException("扣减库存失败");
            }
        //记录成功执行过的事务
            deDuplicationService.insertDeDuplication(localMessage.getTxNo());
        } else {
            System.out.println("=======幂等生效：事务"+deDuplication.getTxNo()
                    +" 已成功执行===========");
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);


    }
}
