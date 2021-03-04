package com.io.agilefast.plugins.opcua.service.impl;/*
package io.agilefast.plugins.opcua.service.impl;

import io.agilefast.common.constant.QueueConstants;
import io.agilefast.common.utils.MapUtils;
import io.agilefast.common.utils.RedisUtil;
import io.agilefast.iotprotocol.constant.Constant;
import io.agilefast.iotprotocol.enums.CollectionStationHandleConf;
import io.agilefast.iotprotocol.enums.ServiceConnectionStatusEnum;
import io.agilefast.iotprotocol.plugins.opcua.ClientGen;
import io.agilefast.iotprotocol.plugins.opcua.KeyStoreLoader;
import io.agilefast.iotprotocol.plugins.opcua.OpcUaProperties;
import io.agilefast.iotprotocol.pojo.entity.IotOpcStationEntity;
import io.agilefast.iotprotocol.pojo.vo.StationAlarmStatesVo;
import io.agilefast.iotprotocol.servive.IotOpcStationService;
import io.agilefast.iotprotocol.servive.ProductionLineMonitorService;
import io.agilefast.iotprotocol.utils.OpcUAUtils;
import io.agilefast.plugins.opcua.client.ClientGen;
import io.agilefast.plugins.opcua.config.OpcUaProperties;
import io.agilefast.plugins.opcua.service.ProductionLineMonitorService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfig;
import org.eclipse.milo.opcua.sdk.client.api.identity.AnonymousProvider;
import org.eclipse.milo.opcua.sdk.client.api.identity.UsernameProvider;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.security.SecurityPolicy;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MonitoringMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoringParameters;
import org.eclipse.milo.opcua.stack.core.types.structured.ReadValueId;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

*/
/**
 * @Author Bernie
 * @Date 2020/9/3/003 11:17
 *//*

@Slf4j
@EnableConfigurationProperties({OpcUaProperties.class})
@Service("productionLineMonitorService")
public class ProductionLineMonitorServiceImpl implements ProductionLineMonitorService {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private OpcUaProperties opcUaConfig;

    @Override
    public void startSubscriptionOpc() {
        // 获取工位信息
        IotOpcStationEntity opcStationEntity = new IotOpcStationEntity();
        //opcStationEntity.setOpcServerIp("opc.tcp://10.107.52.181:49320");
        //opcStationEntity.setStationCode("GG_OP0130A");
        List<IotOpcStationEntity> iotOpcStationEntityList = iotOpcStationService.queryList(opcStationEntity);

        // opcUa 连接处理
        try {
            //OpcUaClient client = ClientGen.opcUaClient;
            List<OpcUaClient> uaClientList = ClientGen.opcUaClientList;
            for (OpcUaClient client : uaClientList) {
                client.connect().get();

                List<OpcUaProperties.OpcUaPropertie> list = opcUaConfig.servers.stream()
                        .filter(opcUaPropertie -> opcUaPropertie.equalEndpointUrl(client.getConfig().getEndpoint().getEndpointUrl()))
                        .collect(Collectors.toList());
                // 遍历对工位进行订阅
                iotOpcStationEntityList.forEach(iotOpcStationEntity -> {
                    if (client.getConfig().getEndpoint().getEndpointUrl().equals(iotOpcStationEntity.getOpcServerIp())) {
                        try {
                            //TODO opcUA服务地址拼接处理
                            String[] addressArray = iotOpcStationEntity.getAddress().split("_");
                            String address = addressArray.length == 1 ? addressArray[0] : addressArray[1];
                            startSubscription(client, "Objects." + address, list.get(0).getClientHandle());
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("opc读取异常");
        }
    }

    @Override
    public Map<String, Object> connectionOpcUAService(List<String> opcUAUrls) {
        log.info("手动连接开始=========================================================================");
        Map<String,Object> result = new HashMap<>();
        // opcUAUrls为空，则全部重新连接
        List<OpcUaProperties.OpcUaPropertie> opcUaPropertieList = CollectionUtils.isEmpty(opcUAUrls) ? opcUaConfig.getServers() :
                opcUaConfig.getServers()
                        .stream()
                        .filter(opcUaPropertie -> opcUAUrls.contains(opcUaPropertie.getEndpointUrl()))
                        .collect(Collectors.toList());
        for (OpcUaProperties.OpcUaPropertie server : opcUaPropertieList) {
            List<EndpointDescription> endpoints;
            try {
                try {
                    endpoints = DiscoveryClient.getEndpoints(server.getEndpointUrl()).get();
                } catch (Throwable ex) {
                    // 发现服务
                    String discoveryUrl = server.getEndpointUrl();

                    if (!discoveryUrl.endsWith("/")) {
                        discoveryUrl += "/";
                    }
                    discoveryUrl += "discovery";

                    log.info("手动开始连接 URL: {}", discoveryUrl);
                    endpoints = DiscoveryClient.getEndpoints(discoveryUrl).get();
                }
                EndpointDescription endpoint = endpoints.stream()
                        .filter(e -> e.getSecurityPolicyUri().equals(ClientGen.securityPolicy.getUri()))
                        .filter(opcUaConfig.endpointFilter())
                        .findFirst()
                        .orElseThrow(() -> new Exception("没有连接上端点"));

                OpcUaClientConfig config = OpcUaClientConfig.builder()
                        .setApplicationName(LocalizedText.english("eclipse milo opc-ua client"))
                        .setApplicationUri("urn:eclipse:milo:examples:client")
                        .setCertificate(ClientGen.loader.getClientCertificate())
                        .setKeyPair(ClientGen.loader.getClientKeyPair())
                        .setEndpoint(endpoint)
                        //根据匿名验证和第三个用户名验证方式设置传入对象 AnonymousProvider（匿名方式）UsernameProvider（账户密码）
                        .setIdentityProvider(server.isAnonymousAccess() ? new AnonymousProvider() : new UsernameProvider(server.getUsername(), server.getPassword()))
                        .setRequestTimeout(uint(5000))
                        .build();

                log.info("使用端点: {} [{}/{}]", endpoint.getEndpointUrl(), ClientGen.securityPolicy, endpoint.getSecurityMode());
                ClientGen.opcUaClientList.add(OpcUaClient.create(config));
                redisUtil.set(Constant.OPCUA_SERVER_STATUS + server.getEndpointUrl(), ServiceConnectionStatusEnum.CONNECTION_SUCCESS);
                result.put(server.getEndpointUrl(), true);
            } catch (Exception e) {
                redisUtil.set(Constant.OPCUA_SERVER_STATUS + server.getEndpointUrl(), ServiceConnectionStatusEnum.CONNECTION_FAILED);
                result.put(server.getEndpointUrl(), false);
                log.error("[{}]客户端创建失败", server.toString());
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> initStationState() {
        List<Map<String,Object>> result = new ArrayList<>();

        List<Object> stationCodes = iotOpcStationService.stationCodes();
        stationCodes.forEach(o -> {
            String[] keys = o.toString().split("_");
            Map<Object, Object> formRedis = redisUtil.hmget(CollectionStationHandleConf.getStationHandle(keys[0]).getHandler() +"_"+ keys[1]);

            Map<Object,Object> map = new HashMap<>();
            for (Map.Entry<Object,Object> entry : formRedis.entrySet()) {
                String key = (String) entry.getKey();
                map.put(key.substring(key.lastIndexOf(".") +1, key.length()),entry.getValue());
            }
            try {
                StationAlarmStatesVo alarmStatesVo = MapUtils.mapToObject(map, StationAlarmStatesVo.class);
                Map<String,Object> hashMap = new HashMap<>();
                hashMap.put("clientHandle",CollectionStationHandleConf.getStationHandle(keys[0]).getHandler());
                hashMap.put("nodeName", keys[1]);
                hashMap.put("stationKey", keys[1]);
                hashMap.put("stationState", alarmStatesVo.getAlarmStatus());
                result.add(hashMap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return result;
    }

    */
/**
     * 开启OPCUA订阅
     *
     * @param client
     * @param opcUAUrl
     * @throws ExecutionException
     * @throws InterruptedException
     *//*

    private void startSubscription(OpcUaClient client, String opcUAUrl, int clientHandle) throws ExecutionException, InterruptedException {
        //创建发布间隔1000ms的订阅对象
        UaSubscription subscription = client.getSubscriptionManager().createSubscription(1000.0).get();


        //创建订阅的变量
        NodeId nodeId = OpcUAUtils.getOpcUANode(client, opcUAUrl).getNodeId().get();
        ReadValueId readValueId = new ReadValueId(nodeId, AttributeId.Value.uid(), null, null);

        //创建监控的参数
        MonitoringParameters parameters = new MonitoringParameters(
                uint(clientHandle),            // clientHandle
                1000.0,     // sampling interval
                null,       // filter, null means use default
                uint(10),   // queue size
                true        // discard oldest
        );

        //创建监控项请求
        //该请求最后用于创建订阅。
        MonitoredItemCreateRequest request = new MonitoredItemCreateRequest(readValueId, MonitoringMode.Reporting, parameters);


        //创建监控项，并且注册变量值改变时候的回调函数。
        List<UaMonitoredItem> items = subscription.createMonitoredItems(
                TimestampsToReturn.Both,
                newArrayList(request),
                (item, id) -> {
                    item.setValueConsumer((uItem, value) -> {
                        String nodeName = (String) uItem.getReadValueId().getNodeId().getIdentifier();
                        // 节点key值为opc服务地址的句柄 + "_" + 工位名称
                        String[] nodeNameArray  = nodeName.split("\\.");
                        String nodeKey =  uItem.getClientHandle() + "_" + nodeNameArray[0];
                        String stationKey = nodeName.substring(nodeName.lastIndexOf("_") + 1, nodeName.length());
                        Map<Object, Object> getFromRedis = redisUtil.hmget(nodeKey);

                        // 数据做到redis缓存
                        getFromRedis.put(stationKey, value.getValue().getValue());
                        redisUtil.hmset(nodeKey, getFromRedis);

                        handlerStationStates(uItem.getClientHandle().intValue(), nodeName, stationKey, getFromRedis);
                        log.info("========================================================");
                        log.info("nodeId :{}", uItem.getReadValueId().getNodeId().getIdentifier());
                        log.info("value :{}", value.getValue().getValue());
                        log.info("========================================================");
                    });
                }
        ).get();
    }

    */
/**
     * 处理工位状态
     *
     * @param nodeName
     * @param stationKey
     * @param formRedis
     *//*

    private void handlerStationStates(int clientHandle, String nodeName, String stationKey, Map<Object, Object> formRedis) {
        try {
            Map<Object,Object> map = new HashMap<>();
            for (Map.Entry<Object,Object> entry : formRedis.entrySet()) {
                String key = (String) entry.getKey();
                map.put(key.substring(key.lastIndexOf(".") +1, key.length()),entry.getValue());
            }
            StationAlarmStatesVo alarmStatesVo = MapUtils.mapToObject(map, StationAlarmStatesVo.class);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("clientHandle",clientHandle);
            jsonObject.put("nodeName", nodeName);
            jsonObject.put("stationKey", stationKey);
            jsonObject.put("stationState", alarmStatesVo.getAlarmStatus());
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);

            Message message = MessageBuilder.withBody(jsonObject.toString().getBytes())
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .setContentEncoding("base64")
                    .setMessageId(UUID.randomUUID() + "")
                    .setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT)
                    .setExpiration("4000")
                    .build();
            amqpTemplate.convertAndSend(QueueConstants.QUEUE_BIG_SCREEN_MONITOR, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void connect(SecurityPolicy securityPolicy, KeyStoreLoader loader) {

    }
}
*/
