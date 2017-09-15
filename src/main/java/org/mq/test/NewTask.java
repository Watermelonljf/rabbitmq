package org.mq.test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by ljianf on 2017/9/15.
 */
public class NewTask {
    public static void main(String[] args) throws Exception{
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("106.14.151.114");//远程IP
        connectionFactory.setPort(5672);//默认端口5672
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        Connection connection = connectionFactory.newConnection();//获取新连接
        Channel channel = connection.createChannel(); //创建信道
        long startTime = System.currentTimeMillis();
        for(int i=0;i<1000;i++){
            String message = "message:"+i;
            channel.basicPublish("", "hello", null, message.getBytes());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("耗时："+(endTime-startTime));
        channel.close();
        connection.close();
    }

}
