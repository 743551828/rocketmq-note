package net.zhangys.base.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * 发送单向消息
 *
 * @author zhangys
 * @date 2021-10-04 01:26
 **/
public class OneWayProducer {


    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        //1.创建消息生产者producer，并制定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //2.指定Nameserver地址
        producer.setNamesrvAddr("1.15.237.49:9876");
        producer.setSendMsgTimeout(8000);
        //3.启动producer
        producer.start();
        for (int i = 0; i < 10; i++) {
            //4.创建消息对象，指定主题Topic、Tag和消息体
            /**
             * 参数一：消息主题 topic
             * 参数二：消息tag
             * 参数三：消息内容
             */
            Message message = new Message("base","Tag3",("Hello world 单向消息" + i).getBytes(StandardCharsets.UTF_8));
            //5.发送消息
            producer.sendOneway(message);
            System.out.println("发送完毕"+i);
            //让线程睡一秒
            TimeUnit.SECONDS.sleep(1);
        }
        //6.关闭生产者producer
        producer.shutdown();
    }


}
