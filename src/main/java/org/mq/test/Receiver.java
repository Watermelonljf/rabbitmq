package org.mq.test;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by ljianf on 2017/9/15.
 */
public class Receiver {
    private final static String QUEUE_NAME = "hello";
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("106.14.151.114");//主机
        connectionFactory.setPort(5672);//默认端口5672
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        Connection connection = connectionFactory.newConnection();//获取新连接
        final Channel channel = connection.createChannel(); //创建信道
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        channel.basicQos(1); // accept only one unack-ed message at a time (see below)
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
             public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                try {
                    System.out.println(" [x] worker C'" + message + "'");
                } finally {
                    System.out.println(" [x] Done");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }
}
