package net.zhangys.base.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * 异步生产者
 * 发送异步消息
 *
 * @author zhangys
 * @date 2021-10-04 01:26
 **/
public class AsyncProducer {


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
            Message message = new Message("base","Tag2",("Hello world" + i).getBytes(StandardCharsets.UTF_8));
            //5.发送消息
            producer.send(message, new SendCallback() {
                /**
                 * 成功回调函数
                 * @param sendResult 成果结果
                 */
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("发送结果："+sendResult);
                }

                /**
                 * 发送异常回调函数
                 * @param e 异常
                 */
                @Override
                public void onException(Throwable e) {
                    System.out.println("发送结果"+e);
                }
            });
            //让线程睡一秒
            TimeUnit.SECONDS.sleep(1);
        }
        //6.关闭生产者producer
        producer.shutdown();
    }


}
