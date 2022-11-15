package com.maeteno.study.java.base.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

@Slf4j
public class SyncProducer {
    public static void main(String[] args) throws Exception {
        // 1. 初始化一个producer并设置Producer group name
        DefaultMQProducer producer = new DefaultMQProducer("demo-test");

        // 2. 设置NameServer地址
        producer.setNamesrvAddr("my-server:9876");
        // 启动producer
        producer.start();
        for (int i = 0; i < 100; i++) {
            // 3. 创建一条消息，并指定topic、tag、body等信息，tag可以理解成标签，对消息进行再归类，RocketMQ可以在消费端对tag进行过滤
            byte[] body = ("Hello RocketMQ " + i).getBytes(StandardCharsets.UTF_8);
            Message msg = new Message("TopicTest", "TagA", body);
            // 4. 利用producer进行发送，并同步等待发送结果
            SendResult sendResult = producer.send(msg);
            log.info("{}", sendResult);
        }

        // 一旦producer不再使用，关闭producer
        producer.shutdown();
    }
}