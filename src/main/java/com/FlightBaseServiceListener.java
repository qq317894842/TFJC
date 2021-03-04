//package com;
//
//import com.caacitc.esb.constants.SdkOrderAction;
//import com.caacitc.esb.listener.XssMessageOrderListener;
//import Util.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * @author dingyi
// * @version v1.0
// * @date 2021-1-1
// * @description 从aliMQ获取FlightBase信息推送到rabbitMQ
// * */
//public class FlightBaseServiceListener extends XssMessageOrderListener {
//    Logger logger = LoggerFactory.getLogger(FlightBaseServiceListener.class);
//    @Override
//    public SdkOrderAction handleMessage(String s) {
//
//        try{
//
//            RabbitMqUtil.postData("FlightBase",s);
//            WriteInFile.WriteFile(s,"FlightBase");
//            logger.info("FlightBase receive: {}",new Object[]{s});
//            return SdkOrderAction.SUCCESS;
//        }catch (Exception e){
//            logger.error("FlightBase error: "+e);
//            return SdkOrderAction.SUSPEND;
//        }
//    }
//}
