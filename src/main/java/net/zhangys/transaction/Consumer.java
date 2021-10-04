package net.zhangys.transaction;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * 消息的消费者
 *
 * @author zhangys
 * @date 2021-10-04 16:32
 **/
public class Consumer {


    public static void main(String[] args) throws MQClientException {
        //1.创建消费者Consumer，制定消费者组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group5");
        //2.指定Nameserver地址
        consumer.setNamesrvAddr("zhangys.net:9876");
        //3.订阅主题Topic和Tag
        consumer.subscribe("TransactionTopic","*");
        //4.设置回调函数，处理消息
        consumer.setMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            for (MessageExt msg : msgs) {
                System.out.println("线程名称【" + Thread.currentThread().getName() + "】:" + new String(msg.getBody()));
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        //5.启动消费者consumer
        consumer.start();
        System.out.println("消费者启动");
    }


}
