//package com;
//
//import Util.RabbitMqUtil;
//import Util.WriteInFile;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.caacitc.esb.client.EsbClient;
//import com.caacitc.esb.dto.ProducerSendResult;
//import com.entity.ReceiveEquipmentEntity;
//import com.caacitc.esb.constants.SdkOrderAction;
//import com.caacitc.esb.listener.XssMessageOrderListener;
//import com.entity.BpmEmEquipmentlistEntity;
//import com.entity.ReturnEquipmentEntity;
//import com.entity.ReturnXmlEntity;
//import com.rabbitmq.client.Connection;
//import com.service.BpmEmEquipmentlistService;
//import com.utils.EquipmentUtils;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * @description 从aliMQ获取设备信息，推送到RabbitMQ
// * */
//public class BackEquipmentServiceListener extends XssMessageOrderListener {
////    public static Connection connection = null;
//    Logger logger = LoggerFactory.getLogger(BackEquipmentServiceListener.class);
//
//    @Autowired
//    @Qualifier("BpmEmEquipmentlistServiceimpl")
//    BpmEmEquipmentlistService bpmEmEquipmentlistService;
//
//    @Override
//    public SdkOrderAction handleMessage(String s) {
//        try{
//            //将全局消息序号进行累加
//            int count=EquipmentUtils.AllMsgNum;
//            if (count==999999)count=0;
//            count++;
//
//            //解析xml获得设备编号
//            ReceiveEquipmentEntity receiveEquipmentEntity =EquipmentUtils.readStringXml(s);
//            logger.info("EQUIPMENT_REQE  请求xml解析完毕。。。。");
//            List<String> rqeqList= receiveEquipmentEntity.getEQUIPMENTNUMBER();
//            List<ReturnEquipmentEntity> list=new ArrayList<>();
//            for (String s1 : rqeqList) {
//                System.err.println("获得的设备编号"+s1);
//            }
//            ReturnXmlEntity metaEntity=new ReturnXmlEntity();
//            metaEntity.setSEQN(count); //消息序号
//            metaEntity.setRMID(receiveEquipmentEntity.getRMID()); //响应id
//            metaEntity.setRCVR(receiveEquipmentEntity.getSNDR()); //消息接受者
//            if (StringUtils.isEmpty(rqeqList.get(0))){
//                System.err.println("进入所有的for");
////                List<BpmEmEquipmentlistEntity> bpmList=bpmEmEquipmentlistService.list();
////                metaEntity.setNUM(bpmList.size());
//                for (int i = 0; i < 3 ; i++) {
//                    BpmEmEquipmentlistEntity entity=new BpmEmEquipmentlistEntity();
//                    entity.setRunStatus(2);
//                    entity.setEquipmentno("A"+i);
//                    entity.setModifyStatusTime(new Date());
//                    list.add(setEquipmentXml(entity));
//                }
//
////                for (BpmEmEquipmentlistEntity entity : bpmList) {
//////                    entity.setRunStatus(2);
//////                    entity.setModifyStatusTime(new Date());
////                    list.add(setEquipmentXml(entity));
////                }
//            }else {
//                metaEntity.setNUM(rqeqList.size());
//                for (String s1 : rqeqList) {
//                    System.err.println("进入for.........");
//                    QueryWrapper<BpmEmEquipmentlistEntity> wrapper=new QueryWrapper<>();
//                    wrapper.eq("EquipmentNo",s1);
////                    BpmEmEquipmentlistEntity entity = bpmEmEquipmentlistService.getOne(wrapper);
//                    BpmEmEquipmentlistEntity entity=new BpmEmEquipmentlistEntity();
//                    entity.setRunStatus(2);
//                    entity.setEquipmentno(s1);
//                    entity.setModifyStatusTime(new Date());
//
//                    list.add(setEquipmentXml(entity));
//                }
//            }
//            metaEntity.setXmlList(list);
//            logger.info("EQUIPMENT_REQE 需要返回过去的xml包装完毕。。。。");
//            //需要返回的xml
//            String sendStr=metaEntity.toString();
//            System.err.println(sendStr);
//            ProducerSendResult producer = EsbClient.getInstance.producer(sendStr);
//            System.err.println("sendStr");
//            String sendState = producer.getSendState();
//            System.out.println(sendState);
//            String sendDesc = producer.getSendDesc();
//            System.out.println(sendDesc);
//            System.out.println("==> "+producer);
////            RabbitMqUtil.postData("EQUIPMENT_REQE",sendStr);
////            WriteInFile.WriteFile(sendStr,"EQUIPMENT_REQE");
////            logger.info("EQUIPMENT_REQE receive: {}",new Object[]{sendStr});
//            return SdkOrderAction.SUCCESS;
//        }catch (Exception e){
//            logger.error("EQUIPMENT_REQE error: "+e);
//            return SdkOrderAction.SUSPEND;
//        }
//    }
//    public ReturnEquipmentEntity setEquipmentXml(BpmEmEquipmentlistEntity entity){
//        //判断这个设备名称属于那一个种类
//        String equipmentName1="转盘";
//        String equipmentName2="称重输送机";
//        String equipmentName3="安检机";
//        String name=entity.getEquipmentname();
//        if (name.indexOf(equipmentName1)>=0){
//            entity.setEquipmentname(equipmentName1);
//        }
//        if (name.indexOf(equipmentName2)>=0){
//            entity.setEquipmentname(equipmentName2);
//        }
//        if (name.indexOf(equipmentName3)>=0){
//            entity.setEquipmentname(equipmentName3);
//        }
//        ReturnEquipmentEntity equipmentXml=new ReturnEquipmentEntity();
//        equipmentXml.setEQUIPMENTNUMBER(entity.getEquipmentno());
//        equipmentXml.setEQUIPMENTTYPE(entity.getEquipmentname());
//        equipmentXml.setEQUIPMENTSTATUS(entity.getRunStatus());
//        equipmentXml.setEQUIPMENTTIME(new Date());
//        return  equipmentXml;
//    }
//
//}
