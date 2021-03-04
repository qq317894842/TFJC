package com.io.agilefast.plugins.opcua.service;

//import com.utils.RedisUtil;
import com.io.agilefast.plugins.opcua.config.OpcUaProperties;
import com.io.agilefast.plugins.opcua.utils.ResolveIPUtil;
import com.io.agilefast.plugins.opcua.utils.SocketUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.Socket;

/**
 * @Author Bernie
 * @Date 2020/9/9/009 14:38
 */
@Slf4j
@Service
public class AddressStatusDetectionService {
//    @Autowired
//    private RedisUtil redisUtil;

    @Async
    public void opcUAServiceState(OpcUaProperties.OpcUaPropertie server) throws InterruptedException {
        ResolveIPUtil.IPAttribute ipAttribute = ResolveIPUtil.getIPAttribute(server.getEndpointUrl());
        SocketUtil socketUtil = new SocketUtil(ipAttribute.getIp(),ipAttribute.getPort());
        while (true) {
            Thread.sleep(30000);
            socketUtil.setClose(socketUtil.isServerClose(socketUtil.getSocket()));
            if (socketUtil.isClose()) {
                log.warn("opcUA[{}:{}]地址连接已断开！",ipAttribute.getIp(),ipAttribute.getPort());
                try{
                    log.info("重新建立连接："+ipAttribute.getIp()+":"+ipAttribute.getPort());
                    InetAddress address = InetAddress.getByName(ipAttribute.getIp());
                    Socket socket = new Socket(address,ipAttribute.getPort());
                    socket.setKeepAlive(true);
                    socket.setSoTimeout(socketUtil.getTimeOut());
                    socketUtil.setSocket(socket);
                    socketUtil.setClose(!socketUtil.Send(socket,"0"));
                    log.info("建立连接成功："+ipAttribute.getIp()+":"+ipAttribute.getPort());
                    //redisUtil.set(Constant.OPCUA_SERVER_STATUS + server.getEndpointUrl(), 1);
                }catch(Exception se){
                    log.warn("创建连接失败:"+ipAttribute.getIp()+":"+ipAttribute.getPort());
                    socketUtil.setClose(true);
                }
            } else {
                socketUtil.ReadText(socketUtil.getSocket());
                //redisUtil.set(Constant.OPCUA_SERVER_STATUS + server.getEndpointUrl(), 1);
                log.info("opcUA[{}:{}]地址连接正常",ipAttribute.getIp(),ipAttribute.getPort());
            }

        }
    }
}
