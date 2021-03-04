package com.utils;

//import com.aliyun.openservices.shade.com.alibaba.fastjson.JSONArray;
//import com.aliyun.openservices.shade.io.netty.util.internal.StringUtil;
import com.entity.ReceiveEquipmentEntity;
import com.entity.Equipment;
import com.service.BpmEmEquipmentlistService;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Auther: HuangRui
 * @Date: 2021/2/9 15:54
 * @Description:
 */
public class EquipmentUtils {
    //全局消息序号累加变量
    public static int AllMsgNum=0;

    @Autowired
    BpmEmEquipmentlistService bpmEmEquipmentlistService;
    /**
     * 获取json数据
     * @return
     */
    public static String getJsonStr(){
        Equipment e1 = new Equipment();
        Equipment e2 = new Equipment();
        e1.setEquipmentNumber("001");
        e1.setEquipmentStatusNew("1");
        e1.setEquipmentStatusOld("0");
        e1.setEquipmentTime(LocalDateTime.now());
        e1.setReason("test 1");
        e2.setEquipmentNumber("002");
        e2.setEquipmentStatusNew("2");
        e2.setEquipmentStatusOld("0");
        e2.setEquipmentTime(LocalDateTime.now());
        e2.setReason("test 2");
        List<Equipment> equipmentList = new ArrayList<>();
        equipmentList.add(e1);
        equipmentList.add(e2);
//        String s = JSONArray.toJSONString(equipmentList);
        return null;
    }

    public static void main(String[] args) {
        String s = "<MSG>\n" +
                "    <META>\n" +
                "        <SNDR>BHS</SNDR>\n" +
                "        <RCVR></RCVR>\n" +
                "        <SEQN>1</SEQN>\n" +
                "        <DDTM>2010010223000</DDTM>\n" +
                "        <TYPE>REQE</TYPE>\n" +
                "        <STYP></STYP>\n" +
                "        <MGID>B20121220234816RE7A97855BA84fd5B</MGID>\n" +
                "        <RMID>A20121220234816RE7A97855BA84fd5B</RMID>\n" +
                "        <APOT>ZUUU</APOT>\n" +
                "    </META>\n" +
                "    <RQEQ>\n" +
                "        <EQUIPMENTNUMBER>A01</EQUIPMENTNUMBER>\n" +
                "        <EQUIPMENTNUMBER>A02</EQUIPMENTNUMBER>\n" +
                "     </RQEQ>\n" +
                "</MSG>";

//        readStringXml(s);
//        System.out.println(StringUtil.isNullOrEmpty(s));
//        解析xml获得设备信息，通过信息查询数据库，获得设备状态，在进行封装，通过mq发送
    }

    /**
     * 将json数组转换为xml数据
     * @param jsonArray mmis系统返回的json 数据
     * @return
     */
//    public static String parseEquipMentXML(JSONArray jsonArray) {
//        StringBuilder strBuilder = new StringBuilder();
//        for(int i=0;i<jsonArray.size();i++){
//            Equipment equipment = jsonArray.getJSONObject(i).toJavaObject(Equipment.class);
//            strBuilder.append("<EQUIPMENT>");
//            if(!StringUtil.isNullOrEmpty(equipment.getEquipmentNumber())){
//                strBuilder.append("<EQUIPMENTNUMBER>");
//                strBuilder.append(equipment.getEquipmentNumber());
//                strBuilder.append("</EQUIPMENTNUMBER>");
//            }
//            if(!StringUtil.isNullOrEmpty(equipment.getEquipmentType())){
//                strBuilder.append("<EQUIPMENTTYPE>");
//                strBuilder.append(equipment.getEquipmentType());
//                strBuilder.append("</EQUIPMENTTYPE>");
//            }
//            if(!StringUtil.isNullOrEmpty(equipment.getEquipmentStatusOld())){
//                strBuilder.append("<EQUIPMENTSTATUSOLD>");
//                strBuilder.append(equipment.getEquipmentStatusOld());
//                strBuilder.append("</EQUIPMENTSTATUSOLD>");
//            }
//            if(!StringUtil.isNullOrEmpty(equipment.getEquipmentStatusNew())){
//                strBuilder.append("<EQUIPMENTSTATUSNEW>");
//                strBuilder.append(equipment.getEquipmentStatusNew());
//                strBuilder.append("</EQUIPMENTSTATUSNEW>");
//            }
//            if(equipment.getEquipmentTime()!=null){
//                strBuilder.append("<EQUIPMENTTIME>");
//                strBuilder.append(equipment.getEquipmentTime());
//                strBuilder.append("</EQUIPMENTTIME>");
//            }
//            if(!StringUtil.isNullOrEmpty(equipment.getReason())){
//                strBuilder.append("<REASON>");
//                strBuilder.append(equipment.getReason());
//                strBuilder.append("</REASON>");
//            }
//            strBuilder.append("</EQUIPMENT>");
//        }
//        return strBuilder.toString();
//    }

    /**
     * @param str json转换的字符串
     * @return 包含头标签的完整xml
     */
    public static String packageXml(String str) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder strBuilder = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        strBuilder.append("<MSG>");
        strBuilder.append("<META>");
        strBuilder.append("<SNDR></SNDR>");//发送者
        strBuilder.append("<RCVR></RCVR>");//接收者  集成是FIMS 不写也可以
        strBuilder.append("<SEQN>1</SEQN>");//轮询序列
        strBuilder.append("<DDTM>").append(LocalDateTime.now().format(fmt)).append("</DDTM>");//发送时间
        strBuilder.append("<TYPE>REQE</TYPE>");//消息类型
        strBuilder.append("<MGID></MGID>");//消息ID
        strBuilder.append("<RMID></RMID>");//响应ID
        strBuilder.append("<APOT></APOT>");//所属区域
        strBuilder.append("</META>");
        strBuilder.append(str);
        strBuilder.append("</MSG>");
        return  strBuilder.toString();
    }

    /**
     * 解析xml 数据
     *
     * @param xml
     * @return
     */

    public static ReceiveEquipmentEntity readStringXml(String xml) {
        ReceiveEquipmentEntity reportBO = new ReceiveEquipmentEntity();
        Document doc = null;
        List<String> rqrqList=new ArrayList<>();
        try {

            String decXml= URLDecoder.decode(xml, "UTF-8");
            System.out.println(decXml);
            doc=DocumentHelper.parseText(decXml);// 将字符串转为XML
            Element rootElt = doc.getRootElement(); // 获取根节点
            Iterator iter = rootElt.elementIterator("RQEQ"); // 获取根节点下的子节点RQEQ
            // 遍历RQEQ节点
            while (iter.hasNext()) {
                Element recordEle = (Element) iter.next();
               List<Element> list=recordEle.elements("EQUIPMENTNUMBER");
                for (Element element : list) {
                    String EQUIPMENTNUMBER=element.getTextTrim();
                    if (StringUtils.isEmpty(EQUIPMENTNUMBER)){
                        System.err.println("解析xml获得设备编号为空");
                    }
                    rqrqList.add(EQUIPMENTNUMBER);
                }
//                String EQUIPMENTNUMBER = recordEle.elementTextTrim("EQUIPMENTNUMBER"); // 拿到head节点下的子节点EQUIPMENTNUMBER值

            }
            Iterator iter2 = rootElt.elementIterator("META");
            while (iter2.hasNext()) {
                Element recordEle = (Element) iter2.next();
                reportBO.setRMID(recordEle.elementTextTrim("MGID"));
                reportBO.setSNDR(recordEle.elementTextTrim("SNDR"));
            }

            reportBO.setEQUIPMENTNUMBER(rqrqList);
            System.err.println("解析请求获得的设备数量："+rqrqList.size());

            return reportBO;
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("设备状态接口解析异常：[{}]"+ e.getMessage());
            return null;
        }
    }
}
