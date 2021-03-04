//import Util.RabbitMqUtil;
//import com.FlightBaseServiceListener;
//import com.FlightServiceListener;
//import com.MainApplication;
////import com.aliyun.openservices.shade.com.alibaba.fastjson.JSONArray;
//import com.caacitc.esb.client.EsbClient;
//import com.caacitc.esb.dto.ProducerSendResult;
//import com.task.ScheduleTask;
//import com.utils.EquipmentUtils;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.UUID;
//
//public class EsbTest {
//
//    public static void main(String[] args) {
//        //文档除了消息类型为BASE的是FLIGHT_BASE,其余都是FLIGHT
//        //接收航班信息
////        EsbClient.getInstance.consumer("FLIGHT",new FlightServiceListener());
//////        //接收航班基础信息
////        EsbClient.getInstance.consumer("FLIGHT_BASE",new FlightBaseServiceListener());
////        String url = "http://39.108.5.84:8088/test/getJson";
////        String jsonStr = MainApplication.doGet(url);
////        System.out.println("获取的json： \n"+jsonStr);
////        //模拟数据
////        JSONArray jsonArray = JSONArray.parseArray(jsonStr);
////        //封装<EQUIPMENT>数据。
////        String equipMentXML = EquipmentUtils.parseEquipMentXML(jsonArray);
////        //<meta>标签信息
////        String xml = EquipmentUtils.packageXml(equipMentXML);
////        //发送
//////        EsbClient.getInstance.consumer("FLIGHT_REQE",new FlightREQEServiceListener());
////        System.out.println("xml: \n"+xml);
////        ProducerSendResult producer = EsbClient.getInstance.producer(xml);
////        String sendState = producer.getSendState();
////        System.out.println(sendState);
////        String sendDesc = producer.getSendDesc();
////        System.out.println(sendDesc);
////        System.out.println("==> "+producer);
//
//
//            Thread thread = new Thread(new ScheduleTask());
//            thread.start();
//
//    }
//}
