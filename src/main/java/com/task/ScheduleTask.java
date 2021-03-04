package com.task;

//import Util.RabbitMqUtil;
import Util.WriteInFile;
import com.*;
//import com.aliyun.openservices.shade.com.alibaba.fastjson.JSONArray;
import com.caacitc.esb.client.EsbClient;
import com.caacitc.esb.dto.ProducerSendResult;
import com.utils.EquipmentUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: HuangRui
 * @Date: 2021/2/20 10:08
 * @Description:
 */
public class ScheduleTask implements Runnable {
    @Override
    public void run() {
        try {
            //接收航班信息
//            EsbClient.getInstance.consumer("FLIGHT",new FlightServiceListener());
            //接收设备信息
//            EsbClient.getInstance.consumer("EQUIPMENT_REQE",new BackEquipmentServiceListener());
//        //接收航班基础信息
//            EsbClient.getInstance.consumer("FLIGHT_BASE",new FlightBaseServiceListener());


//
//            String url = "http://39.108.5.84:8088/test/getJson";//换成 mmis地址
//            String jsonStr = MainApplication.doGet(url);
//            System.out.println("获取的json： \n"+jsonStr);
//            if(jsonStr==null || jsonStr.isEmpty()){
//                return;
//            }
//            //模拟数据
//            JSONArray jsonArray = JSONArray.parseArray(jsonStr);
//            //封装<EQUIPMENT>数据。
//
//            String equipMentXML = EquipmentUtils.parseEquipMentXML(jsonArray);
//            //<meta>标签信息
//            String xml = EquipmentUtils.packageXml(equipMentXML);

            //发送
            SimpleDateFormat f=new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String time=f.format(new Date());
            String xml="<MSG>\n" +
                    "<META>\n" +
                    "<SNDR>BHS</SNDR>\n" +
                    "<RCVR></RCVR>\n" +
                    "<SEQN>"+1+"</SEQN>\n" +
                    "<DDTM>"+time+"</DDTM>\n" +
                    "<TYPE>BHSE</TYPE>\n" +
                    "<STYP>BRUE</STYP>\n" +
                    "       <MGID>A20121220234816RE7A97855BA84fd5B</MGID>\n" +
                    "       <RMID></RMID>\n" +
                    "       <APOT>ZUTF</APOT>\n" +
                    "</META>\n" +
                    "<EQUIPMENT>\n" +
                    "<EQUIPMENTNUMBER>A01</EQUIPMENTNUMBER>\n" +
                    "       <EQUIPMENTTYPE></EQUIPMENTTYPE>\n" +
                    "       <EQUIPMENTSTATUSOLD>"+1+"</EQUIPMENTSTATUSOLD>\n" +
                    "       <EQUIPMENTSTATUSNEW>"+2+"</EQUIPMENTSTATUSNEW>\n" +
                    "       <EQUIPMENTTIME>2022-02-24 14:11:11.000</EQUIPMENTTIME>\n" +
                    "       <REASON></REASON>\n" +
                    "   </EQUIPMENT>\n" +
                    "</MSG>";
//
//        EsbClient.getInstance.consumer("EQUIPMENT_REQE",new ZhuDongServiceListener());
            System.out.println("xml: \n"+xml);
            //通过这个主动发送消息到他们的jar包
            ProducerSendResult producer = EsbClient.getInstance.producer(xml);
            String sendState = producer.getSendState();
            System.out.println(sendState);
            String sendDesc = producer.getSendDesc();
            System.out.println(sendDesc);
            System.out.println("==> "+producer);
            Thread.sleep(60000L*5L);//5分钟轮询一次
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
