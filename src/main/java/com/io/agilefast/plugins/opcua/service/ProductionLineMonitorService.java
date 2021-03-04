package com.io.agilefast.plugins.opcua.service;

import java.util.List;
import java.util.Map;

/**
 * @Author Bernie
 * @Date 2020/9/3/003 11:14
 */
public interface ProductionLineMonitorService {
    /**
     * 启动订阅opcUA
     */
    public void startSubscriptionOpc();

    /**
     * 连接opcUA 服务
     * @param opcUAUrls opcUA服务地址
     * @return 状态信息
     */
    public Map<String,Object> connectionOpcUAService(List<String> opcUAUrls);

    /**
     * 初始化工位状态信息，从redis取
     * @return
     */
    List<Map<String, Object>> initStationState();

}
