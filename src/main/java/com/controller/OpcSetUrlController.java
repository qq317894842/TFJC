package com.controller;

import com.io.agilefast.plugins.opcua.client.ClientGen;
import com.io.agilefast.plugins.opcua.utils.OpcUAUtils;
import com.io.agilefast.plugins.opcua.vo.OpcUAVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.nodes.Node;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * 根据节点和节点值，写入数据库
 * */
@Slf4j
@Api(tags = "写入opcurl的借款")
@RestController
@RequestMapping("/OpcUrlNodes")
public class OpcSetUrlController {

//    @GetMapping("/add")
//    @ApiOperation(value = "添加opc节点值", notes = "根据给定的value值操作数据库")
//    public void sendUnMsg(String opcUAUrl,String nodevalue,String equipmentno) throws ExecutionException, InterruptedException {
//        log.info("OPCUA写操作-开始");
//        OpcUAVo opcUAVo = new OpcUAVo();
//        opcUAVo.setValue(nodevalue);
//        opcUAVo.setOpcUAUrl(opcUAUrl);
//        OpcUaClient client = ClientGen.opcUaClientList.get(0);
//        client.connect().get();
//        //在地址中写入节点值，这个值是数组还是单个
//        Node node = OpcUAUtils.setOpcUANode(client, opcUAUrl,"5");
//
//        log.info("获取到的节点值：Name:[{}], ns:[{}]", node.getBrowseName().get().getName(), node.getBrowseName().get().getNamespaceIndex());
//        //获取到nodeid
//        NodeId counter1 = node.setNodeId().get();
//        //最后写入
//        String strCounter1 = "";
//        DataValue counter1value = client.readValue(1, TimestampsToReturn.Both, counter1).get();
//        strCounter1 = counter1value.getValue().getValue().toString();
//        log.info("节点值：" + strCounter1);
//        log.info("OPCUA读操作-结束");
//        //更改运行时间
//        if(entity.getRuntime()==null||entity.getRuntime()!=Double.parseDouble(strCounter1)) {
//            entity.setRuntime(Double.parseDouble(strCounter1));
//            bpmEmEquipmentlistService.updateById(entity);
//        }
//    }
}

