//package com;
//
//import com.caacitc.esb.constants.SdkOrderAction;
//import com.caacitc.esb.listener.XssMessageOrderListener;
//import Util.*;
//import com.rabbitmq.client.Connection;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * @author dingyi
// * @version 1.0
// * @date 2021-1-1
// * @description 从aliMQ获取航班数据，推送到RabbitMQ
// * */
//public class FlightServiceListener extends XssMessageOrderListener {
//    public static Connection connection = null;
//    Logger logger = LoggerFactory.getLogger(FlightServiceListener.class);
//    @Override
//    public SdkOrderAction handleMessage(String s) {
//        try{
//            RabbitMqUtil.postData("Flight",s);
//            WriteInFile.WriteFile(s,"Flight");
//            logger.info("FlightBase receive: {}",new Object[]{s});
//            return SdkOrderAction.SUCCESS;
//        }catch (Exception e){
//            logger.error("Flight error: "+e);
//            return SdkOrderAction.SUSPEND;
//        }
//    }
//}
