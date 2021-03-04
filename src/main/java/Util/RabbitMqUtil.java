//package Util;
//
//import com.FlightServiceListener;
//import com.rabbitmq.client.*;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//import java.util.concurrent.TimeoutException;
//
//public class RabbitMqUtil {
//    public static void postData(String queue, String data) {
//        checkchannel();
//        postTomq(queue, FlightServiceListener.connection,RabbitMqUtil.channel,data);
//    }
//
//    //发送到mq
//    public static void postTomq(String QUEUE_NAME,Connection connection,Channel channel,String message1){
//        try {
//            Map<String, Object> argss = new HashMap<String , Object>();
//            argss.put("x-message-ttl" , Integer.parseInt("10000000"));
//            argss.put("x-max-length",Integer.parseInt("10000"));
//            channel.queueDeclare(QUEUE_NAME, true, false, false, argss);
//            channel.queueBind(QUEUE_NAME,"tf","rk");
//            //有多个consumer时，每个consumer会轮询着获取queue
//            channel.basicPublish("tf", "rk", null, message1.getBytes("UTF-8"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    private static String Host = "";
//    private static String VirtualHost = "";
//    private static String Username = "";
//    private static String Password = "";
//    private static int Port = 0;
//    public static Channel channel = null;
//    //mq具体做的操作
//    public static ConnectionFactory getFactory(){
//        Properties properties = new Properties();
//        try {
//            properties.load(new FileInputStream(new File("D:/TFJC/mq.properties").getAbsolutePath()));
//            Host = properties.getProperty("Host");
//            VirtualHost = properties.getProperty("VirtualHost");
//            Username = properties.getProperty("Username");
//            Password = properties.getProperty("Password");
//            Port = Integer.parseInt(properties.getProperty("Port"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost(Host);
//        factory.setVirtualHost(VirtualHost);
//        factory.setUsername(Username);
//        factory.setPassword(Password);
//        factory.setPort(Port);
//        factory.setAutomaticRecoveryEnabled(false);
//        return factory;
//    }
//    public static boolean connMQ(Connection connection){
//        Properties properties = new Properties();
//        try {
//            properties.load(new FileInputStream(new File("D:/TFJC/mq.properties").getAbsolutePath()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //OA test
//        if(FlightServiceListener.connection==null){
//            ConnectionFactory factory = getFactory();
//            try {
//                FlightServiceListener.connection = factory.newConnection();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (TimeoutException e) {
//                e.printStackTrace();
//            }
//        }
//        return true;
//    }
//    public static void checkchannel() {
//        if(RabbitMqUtil.channel==null){
//            try {
//                RabbitMqUtil.connMQ(FlightServiceListener.connection);
//                RabbitMqUtil.channel = FlightServiceListener.connection.createChannel();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
