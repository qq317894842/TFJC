package com.service.Impl;

//import Util.RabbitMqUtil;
import Util.WriteInFile;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.caacitc.esb.client.EsbClient;
import com.caacitc.esb.dto.ProducerSendResult;
import com.entity.OpcEquipmentEntity;
import com.service.OpcUAScadaService;
import com.entity.BpmEmEquipmentlistEntity;
import com.service.BpmEmEquipmentlistService;
//import io.agilefast.modules.bms.service.BpmEmEquipmentstatelogService;
import com.io.agilefast.plugins.opcua.client.ClientGen;
import com.io.agilefast.plugins.opcua.utils.OpcUAUtils;
import com.io.agilefast.plugins.opcua.vo.OpcUAVo;
import com.utils.EquipmentUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.nodes.Node;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MonitoringMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoringParameters;
import org.eclipse.milo.opcua.stack.core.types.structured.ReadValueId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.google.common.collect.Lists.newArrayList;
import static org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

@Slf4j
@Service("opcUAScadaService")
public class OpcUAScadaServiceImpl implements OpcUAScadaService {
    @Autowired
    @Qualifier("BpmEmEquipmentlistServiceimpl")
    private BpmEmEquipmentlistService bpmEmEquipmentlistService;
//    @Autowired
//    private BpmEmEquipmentstatelogService bpmEmEquipmentstatelogService;

    SimpleDateFormat f=new SimpleDateFormat("yyyyMMddHHmmssSSS");

    @Override
    public void opcUASubscription() {
        try {
            log.info("opcUA采集服务启动中......");
            System.err.println("opcUA采集服务启动中");
            OpcUAVo opcUAVo = new OpcUAVo();
            List<OpcUAVo> opcUAVoList =new ArrayList<>();
            //查询所有opcurl所对应的数据
            List<BpmEmEquipmentlistEntity> bpmEmEquipmentlistEntities = bpmEmEquipmentlistService.list(
                    new QueryWrapper<BpmEmEquipmentlistEntity>()
                            .isNotNull("OpcUAUrl")
                            .ne("OpcUAUrl","")
                            .like("EquipmentName","称重输送机").or()
                            .like("EquipmentName","转盘").or()
                            .like("EquipmentName","安检机")

            );
            //获取到opcurl放入opc实体类
            for (BpmEmEquipmentlistEntity entity:bpmEmEquipmentlistEntities
            ) {
                OpcUAVo opcUAVoStatus= new OpcUAVo();

                opcUAVoStatus.setOpcUAUrl(entity.getOpcuaurl()+".Status");
                opcUAVoList.add(opcUAVoStatus);
            }
            opcUAVo.setOpcUAVoList(opcUAVoList);

            OpcUaClient client = ClientGen.opcUaClientList.get(0);
            client.connect().get();
            // 遍历开启监控（监控所含OpcUAUrl的变化），批量订阅
            opcUAVo.getOpcUAVoList().forEach(opcUAVo1 -> {
                try {
                    startSubscription(client,opcUAVo1.getOpcUAUrl());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            log.info("opcUA采集服务启动完成......");

        } catch (Exception e) {
            log.info("opcUA采集服务启动错误"+e.getMessage());
            e.printStackTrace();
        }
    }


    private void startSubscription(OpcUaClient client, String opcUAUrl) throws ExecutionException, InterruptedException {
        //创建发布间隔1000ms的订阅对象
        UaSubscription subscription = client.getSubscriptionManager().createSubscription(1000.0).get();
        //创建监控的参数
        MonitoringParameters parameters = new MonitoringParameters(
                uint(1),
                1000.0,     // sampling interval
                null,       // filter, null means use default
                uint(10),   // queue size
                true        // discard oldest
        );
        //创建订阅的变量
        NodeId nodeId = new NodeId(2, opcUAUrl);
//        NodeId nodeId = OpcUAUtils.getOpcUANode(client, opcUAUrl).getNodeId().get();
        ReadValueId readValueId = new ReadValueId(nodeId, AttributeId.Value.uid(), null, null);

        //创建监控项请求
        //该请求最后用于创建订阅。
        MonitoredItemCreateRequest request = new MonitoredItemCreateRequest(readValueId, MonitoringMode.Reporting, parameters);

        //创建监控项，并且注册变量值改变时候的回调函数。
        List<UaMonitoredItem> items = subscription.createMonitoredItems(
                TimestampsToReturn.Both,
                newArrayList(request),
                (item, id) -> {
                    item.setValueConsumer((uItem, value) -> {
//                        System.out.println("nodeId :" + uItem.getReadValueId().getNodeId());
//                        System.out.println("value :" + value.getValue().getValue());
                        log.info("OPC采集成功：nodeId :"+uItem.getReadValueId().getNodeId()+",value :"+value.getValue().getValue()+",Identifier :"+uItem.getReadValueId().getNodeId().getIdentifier());
                        //如果状态有变化，则执行该方法，value就是监控变化得到的数值
                        updateEquList(uItem.getReadValueId().getNodeId().toString(),uItem.getReadValueId().getNodeId().getIdentifier().toString(),value.getValue().getValue().toString());

                    });
                }
        ).get();
    }

    private void updateEquList(String nodeId,String Identifier,String value){
        try {
                int number=Integer.parseInt(value);
                //nodeid就是之前在设置opcurl之后的值，判断是否含有这个值执行对应方法
                if( nodeId.contains("Status")) {
                    //这里全是是获取指定opcurl单个对象
                    List<BpmEmEquipmentlistEntity> bpmEmEquipmentlistEntitiesRuntime = bpmEmEquipmentlistService.getListByOpcUAUrl(Identifier.replace(".Status", ""));
                    for (BpmEmEquipmentlistEntity entity : bpmEmEquipmentlistEntitiesRuntime) {
//                        if (entity.getRuntime() == null || entity.getRuntime() != Double.parseDouble(value)) {
                            //加锁
                            Lock lock=new ReentrantLock();
                            try {
                                lock.lock();
                                int runstatus=0;
                                boolean flag=false; //标识他们是不可用的，就不需要进行发送信息
                                int oldrunstatus=entity.getRunStatus();//获得旧状态
                                System.err.println("获得的状态整数值："+number);
                                String str=Integer.toBinaryString(number); //将监控得到的变化数值转成二进制
                                //判断这个设备名称属于那一个种类
                                String equipmentName1="转盘";
                                String equipmentName2="称重输送机"; //称重输送机
                                String equipmentName3="安检机";
                                String name=entity.getEquipmentname();
                                if (name.indexOf(equipmentName1)>=0){
                                    //先通过各规则判断0 1来获得当前设备的状态是可用还是不可用(1)
                                    //按照规则获取二进制的位数值（从0开始）
                                    if (str.charAt(5)==1||str.charAt(6)==1||str.charAt(7)==1
                                            ||str.charAt(9)==1||str.charAt(10)==1||str.charAt(11)==1
                                            ||str.charAt(13)==1||str.charAt(14)==1||str.charAt(15)==1){
                                        flag=true;
                                        runstatus=0;
                                    }else {
                                        //转盘如果s为1就是可用，0停用，数据库可用是1 停用是2 不可用是0
                                        if (str.charAt(0)==1||str.charAt(4)==1){
                                            runstatus=1;
                                        }else{
                                            runstatus=2;
                                        }
                                        entity.setEquipmentname(equipmentName1);
                                    }
                                }
                                if (name.indexOf(equipmentName2)>=0){
                                    for (int i = 0; i < 15 ; i++) {
                                            int s=str.charAt(i);
                                            if (s==1){
                                                flag=true;
                                                runstatus=0;
                                                return;
                                            }
                                    }
                                    if (!flag){
                                        //称重输送机s为1可用，0停用
                                        if (str.charAt(19)==1){
                                            runstatus=1;
                                        }else{
                                            runstatus=2;
                                        }
                                        entity.setEquipmentname(equipmentName2);
                                    }

                                }
                                if (name.indexOf(equipmentName3)>=0){
                                    for (int i = 0; i < 19 ; i++) {
                                        if (i!=5){
                                            int s=str.charAt(i);
                                            if (s==1){
                                                flag=true;
                                                runstatus=0;
                                                return;
                                            }
                                        }
                                    }

                                    if (!flag){
                                        //安检机如果s为0就是可用，1停用
                                        if (str.charAt(5)==0){
                                            runstatus=1;
                                        }else{
                                            runstatus=2;
                                        }
                                        entity.setEquipmentname(equipmentName3);
                                    }
                                }
                                entity.setRunStatus(runstatus);
                                System.err.println("查询到的状态："+runstatus);
                                entity.setModifyStatusTime(new Date());
                                entity.setStatusReportTime(new Date());
                                entity.setIsStatusReport(false);
                                //旧状态如果为不可用就不更新数据库，但是其他信息还是需要发送
                                if (oldrunstatus!=0)
                                bpmEmEquipmentlistService.updateById(entity);
                                log.info("OPC 数据库设备运行状态修改成功！！");
                                if (flag){
                                    return;
                                }
                                //将全局消息序号进行累加
                                int count= EquipmentUtils.AllMsgNum;
                                if (count==999999)count=0;
                                count++;
                                OpcEquipmentEntity opcEquipmentEntity=new OpcEquipmentEntity();
                                opcEquipmentEntity.setEQUIPMENTNUMBER(entity.getEquipmentno());
                                opcEquipmentEntity.setEQUIPMENTTYPE(entity.getEquipmentname()); //种类
                                opcEquipmentEntity.setEQUIPMENTSTATUSNEW(entity.getRunStatus());
                                opcEquipmentEntity.setEQUIPMENTTIME(entity.getModifyStatusTime());
                                opcEquipmentEntity.setEQUIPMENTSTATUSOLD(oldrunstatus); //旧状态
                                opcEquipmentEntity.setREASON(""); //问题信息
                                opcEquipmentEntity.setSEQN(count);
                                //包装成xml
                                String sendStr=opcEquipmentEntity.toString();
                                System.out.println("xml: \n"+sendStr);
                                //通过这个主动发送消息到他们的jar包
                                ProducerSendResult producer = EsbClient.getInstance.producer(sendStr);
                                String sendState = producer.getSendState();
                                System.out.println(sendState);
                                String sendDesc = producer.getSendDesc();
                                System.out.println(sendDesc);
                                System.out.println("==> "+producer);
                                log.info("OPC 主动返回的设备状态数据xml完成。。。。");

                            }catch (Exception e){
                                e.printStackTrace();
                            }finally {
                                lock.unlock();
                            }
//                        }
                    }
                }
        }
        catch (Exception ex){

        }
    }

    @Override
    public void opcUARead(){
        List<BpmEmEquipmentlistEntity> bpmEmEquipmentlistEntities = bpmEmEquipmentlistService.list(
                new QueryWrapper<BpmEmEquipmentlistEntity>()
                        .isNotNull("OpcUAUrl")
                        .ne("OpcUAUrl","")
        );
        for (BpmEmEquipmentlistEntity entity:bpmEmEquipmentlistEntities
        ) {
            String opcUAUrl=entity.getOpcuaurl()+".Status";

            String strCounter1 = "";
            String nodeName = "";
            try {
                log.info("OPCUA读操作-开始");
                OpcUaClient client = ClientGen.opcUaClientList.get(0);
                client.connect().get();

                Node node = OpcUAUtils.getOpcUANode(client, opcUAUrl);
                nodeName = node.getBrowseName().get().getName();
                log.info("获取到的节点值：Name:[{}], ns:[{}]", node.getBrowseName().get().getName(), node.getBrowseName().get().getNamespaceIndex());
                NodeId counter1 = node.getNodeId().get();

                DataValue counter1value = client.readValue(1, TimestampsToReturn.Both, counter1).get();
                strCounter1 = counter1value.getValue().getValue().toString();
                log.info("节点值：" + strCounter1);
                log.info("OPCUA读操作-结束");
                if(entity.getRuntime()==null||entity.getRuntime()!=Double.parseDouble(strCounter1)) {
                    entity.setRuntime(Double.parseDouble(strCounter1));
                    bpmEmEquipmentlistService.updateById(entity);
                }

            } catch (Exception e) {
                log.error("获取服务节点失败,错误信息：{}", e.getMessage());
            }
//            log.info("节点[" + nodeName + "]值:"+ strCounter1);
        }
    }
}
