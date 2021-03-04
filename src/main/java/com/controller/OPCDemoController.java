/**
 * @Title: OPCDemoController
 * @ProjectName agilefast-framework
 * @Package io.agilefast.modules.opcua.controller
 * @Description: TODO
 * @author daixirui
 * @version V1.0.0
 * @Copyright: 2020  All rights reserved.
 * @date 2020/5/7 14:13
 */
package com.controller;

import com.utils.R;
//import io.agilefast.modules.bms.service.BpmEmEquipmentstatelogService;
import com.io.agilefast.plugins.opcua.client.ClientGen;
import com.io.agilefast.plugins.opcua.utils.OpcUAUtils;
import com.io.agilefast.plugins.opcua.vo.OpcUAVo;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.nodes.Node;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.*;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MonitoringMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoringParameters;
import org.eclipse.milo.opcua.stack.core.types.structured.ReadValueId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;

import static com.google.common.collect.Lists.newArrayList;
import static org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

@Slf4j
@Api(tags = "OPCUA测试接口")
@RestController
@RequestMapping("/opcUa")
public class OPCDemoController {

//    @Autowired
//    private BpmEmEquipmentstatelogService bpmEmEquipmentstatelogService;

    private final AtomicLong clientHandles = new AtomicLong(1L);

    @GetMapping("/read")
    @ApiOperation(value = "读取指定地址的opcUAUrl值", notes = "根据给定的opcUAUrl读取对应的值")
    @ApiImplicitParam(name = "opcUAUrl", value = "opcUA地址路径", example = "Objects.StaticData.StaticVariables.Boolean")
    public R readDemo(String opcUAUrl) {
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

        } catch (Exception e) {
            log.error("获取服务节点失败,错误信息：{}", e.getMessage());
            e.printStackTrace();
        }
        return R.ok().put("节点[" + nodeName + "]值", strCounter1);
    }


    @GetMapping("subscriptionDemo")
    @ApiOperation(value = "订阅指定地址的opcUAUrl值", notes = "根据给定的opcUAUrl订阅读取对应的值")
    public R subscriptionDemo(String opcUAUrl) {
        try {
            log.info("开始监听");
            OpcUaClient client = ClientGen.opcUaClientList.get(0);
            client.connect().get();

            //创建发布间隔1000ms的订阅对象
            UaSubscription subscription = client.getSubscriptionManager().createSubscription(1000.0).get();
//            log.info("开始监听1"+OpcUAUtils.getOpcUANode(client, opcUAUrl));
//            log.info("开始监听2"+OpcUAUtils.getOpcUANode(client, opcUAUrl).getNodeId());
//            log.info("开始监听3"+OpcUAUtils.getOpcUANode(client, opcUAUrl).getNodeId().get());

            //namespaceIndex可以根据OpcUAUtils.getOpcUANode方法获取，用调试方法，查看其值
            NodeId counter1 = new NodeId(2, opcUAUrl);
//            NodeId counter1 = OpcUAUtils.getOpcUANode(client, opcUAUrl).getNodeId().get();

            // 监听服务当前时间节点
            ReadValueId readValueId = new ReadValueId(
                    counter1,
                    AttributeId.Value.uid(), null, QualifiedName.NULL_VALUE);

            // 每个项目的客户端句柄必须是唯一的
            UInteger clientHandle = uint(clientHandles.getAndIncrement());

            MonitoringParameters parameters = new MonitoringParameters(
                    clientHandle,
                    // 间隔
                    1000.0,
                    // 过滤器空表示使用默认值
                    null,
                    // 队列大小
                    uint(50),
                    // 是否丢弃旧配置
                    true
            );

            MonitoredItemCreateRequest request = new MonitoredItemCreateRequest(
                    readValueId, MonitoringMode.Reporting, parameters);

            // 创建监控项，并且注册变量值改变时候的回调函数。
            BiConsumer<UaMonitoredItem, Integer> onItemCreated =
                    (item, id) -> item.setValueConsumer(this::onSubscriptionValue);

            List<UaMonitoredItem> items = subscription.createMonitoredItems(
                    TimestampsToReturn.Both,
                    newArrayList(request),
                    onItemCreated
            ).get();

            for (UaMonitoredItem item : items) {
                if (item.getStatusCode().isGood()) {
                    log.info("item created for nodeId={}", item.getReadValueId().getNodeId());
                } else {
                    log.warn(
                            "failed to create item for nodeId={} (status={})",
                            item.getReadValueId().getNodeId(), item.getStatusCode());
                }
            }

        } catch (Exception e) {
            log.error("订阅失败" + e.getMessage());
        }
        return null;
    }

    /**
     * @return void
     * @Author daixirui
     * @Description //TODO OPCUA订阅回调函数
     * @Date 14:38 2020/5/7
     * @Param [item, value]
     **/
    private void onSubscriptionValue(UaMonitoredItem item, DataValue value) {
        log.info(
                "subscription value received: item={}, value={}",
                item.getReadValueId().getNodeId(), value.getValue());
    }


    @PostMapping("batchSubscriptionDemo")
    @ApiOperation(value = "批量订阅指定opcUAUrl值", notes = "根据给定的opcUAUrl批量订阅读取对应的值")
    public R subscriptionDemo(OpcUAVo opcUAVo) {
        try {
            OpcUaClient client = ClientGen.opcUaClientList.get(0);
            client.connect().get();
            // 遍历开启监控
            opcUAVo.getOpcUAVoList().forEach(opcUAVo1 -> {
                try {
                    startSubscription(client,opcUAVo1.getOpcUAUrl());
                } catch (ExecutionException e) {
                    log.error("批量订阅失败startSubscription ExecutionException" + e.getMessage());
                } catch (InterruptedException e) {
                    log.error("批量订阅失败startSubscription InterruptedException" + e.getMessage());
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            log.error("批量订阅失败client" + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    private void startSubscription(OpcUaClient client, String opcUAUrl) throws ExecutionException, InterruptedException {
        //创建发布间隔1000ms的订阅对象
        UaSubscription subscription = client.getSubscriptionManager().createSubscription(1000.0).get();
        //创建监控的参数
        MonitoringParameters parameters = new MonitoringParameters(
                uint(1),
                1000.0,     // sampling interval
                null,       // filter, null means use default
                uint(50),   // queue size
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
                        log.info("nodeId :" + uItem.getReadValueId().getNodeId());
                        log.info("opcuaurl :" + uItem.getReadValueId().getNodeId().getIdentifier());
                        log.info("value :" + value.getValue().getValue());
                    });
                }
        ).get();
    }
}
