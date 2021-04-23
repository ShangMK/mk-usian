package com.usian.template;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

public class AmqpTemplate {
    /**
     * 建立与RabbitMQ的连接
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {
        //定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置服务地址
        factory.setHost("192.168.2.101");
        //端口
        factory.setPort(5672);
        //设置账号信息，用户名、密码、vhost
        factory.setVirtualHost("/usian");
        factory.setUsername("admin");
        factory.setPassword("1111");
        // 通过工程获取连接
        Connection connection = factory.newConnection();
        return connection;
    }

    public static void convertAndSend(String exchange, String duilie, String itemid)throws Exception {
        // 获取到连接
        Connection connection = AmqpTemplate.getConnection();
        // 获取通道
        Channel channel = connection.createChannel();
        // 声明exchange，指定类型为topic
        channel.exchangeDeclare(exchange, "topic");
        // 消息内容
        String message =itemid;
        // 发送消息，并且指定routing key 为：insert ,代表新增商品
        channel.basicPublish(duilie, "item.add", null, message.getBytes());
        System.out.println(" [商品服务：] Sent '" + message + "'");

    }
}
