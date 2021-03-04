package com.io.agilefast.plugins.opcua.client;


import com.io.agilefast.plugins.opcua.config.OpcUaProperties;
import com.io.agilefast.plugins.opcua.service.AddressStatusDetectionService;
import com.io.agilefast.plugins.opcua.utils.OpcUAUtils;
import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.collections.CollectionUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfig;
import org.eclipse.milo.opcua.sdk.client.api.identity.AnonymousProvider;
import org.eclipse.milo.opcua.sdk.client.api.identity.UsernameProvider;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.security.SecurityPolicy;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

/**
 * @Author daixirui
 * @Description //TODO OPCUA客户端对象
 * @Date 15:36 2020/5/7
 * @Param
 * @return
 **/
@Slf4j
@Configuration
@EnableConfigurationProperties({OpcUaProperties.class})
public class ClientGen {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private final CompletableFuture<OpcUaClient> future = new CompletableFuture<>();

    public static OpcUaClient opcUaClient;
    public static List<OpcUaClient> opcUaClientList = new ArrayList<>();
    public static SecurityPolicy securityPolicy;
    public static KeyStoreLoader loader;

    @Autowired
    private OpcUaProperties opcUaConfig;

    @Autowired
    private AddressStatusDetectionService addressStatusDetectionService;


    @PostConstruct
    public void createClient() {
        try {
            if (CollectionUtils.isEmpty(opcUaConfig.getServers())) throw new Exception("opcUa的服务地址配置为空");

            Path securityTempDir = Paths.get(System.getProperty("java.io.tmpdir"), "security");
            Files.createDirectories(securityTempDir);
            if (!Files.exists(securityTempDir)) {
                throw new Exception("没有创建安全目录: " + securityTempDir);
            }
            log.info("安全目录: {}", securityTempDir.toAbsolutePath());

            //加载秘钥
            loader = new KeyStoreLoader().load(securityTempDir);

            //安全策略 None、Basic256、Basic128Rsa15、Basic256Sha256
            securityPolicy = SecurityPolicy.None;

            this.connect(securityPolicy,loader);
           /* List<EndpointDescription> endpoints;

            try {

                endpoints = DiscoveryClient.getEndpoints(opcUaConfig.getServers().get(0).getEndpointUrl()).get();
            } catch (Throwable ex) {
                // 发现服务
                String discoveryUrl = opcUaConfig.getServers().get(0).getEndpointUrl();

                if (!discoveryUrl.endsWith("/")) {
                    discoveryUrl += "/";
                }
                discoveryUrl += "discovery";

                log.info("开始连接 URL: {}", discoveryUrl);
                endpoints = DiscoveryClient.getEndpoints(discoveryUrl).get();
            }
            EndpointDescription endpoint = endpoints.stream()
                    .filter(e -> e.getSecurityPolicyUri().equals(securityPolicy.getUri()))
                    .filter(opcUaConfig.endpointFilter())
                    .findFirst()
                    .orElseThrow(() -> new Exception("没有连接上端点"));

            log.info("使用端点: {} [{}/{}]", endpoint.getEndpointUrl(), securityPolicy, endpoint.getSecurityMode());

            OpcUaClientConfig config = OpcUaClientConfig.builder()
                    .setApplicationName(LocalizedText.english("eclipse milo opc-ua client"))
                    .setApplicationUri("urn:eclipse:milo:examples:client")
                    .setCertificate(loader.getClientCertificate())
                    .setKeyPair(loader.getClientKeyPair())
                    .setEndpoint(endpoint)
                    //根据匿名验证和第三个用户名验证方式设置传入对象 AnonymousProvider（匿名方式）UsernameProvider（账户密码）
                    //new UsernameProvider("admin","123456")
                    .setIdentityProvider(new AnonymousProvider())
                    //.setIdentityProvider(new UsernameProvider(opcUaConfig.getUsername(),opcUaConfig.getPassword()))
                    .setRequestTimeout(uint(5000))
                    .build();
            opcUaClient = OpcUaClient.create(config);*/
        } catch (Exception e) {
            e.printStackTrace();
            log.error("创建客户端失败" + e.getMessage());
        }
    }

    private void connect(SecurityPolicy securityPolicy, KeyStoreLoader loader) throws InterruptedException {
        for (OpcUaProperties.OpcUaPropertie server : opcUaConfig.getServers()) {
            List<EndpointDescription> endpoints;
            try {
                try {
                    endpoints = DiscoveryClient.getEndpoints(server.getEndpointUrl()).get();
                } catch (Throwable ex) {
                    ex.printStackTrace();
                    // 发现服务
                    String discoveryUrl = server.getEndpointUrl();

                    if (!discoveryUrl.endsWith("/")) {
                        discoveryUrl += "/";
                    }
                    discoveryUrl += "discovery";

                    log.info("开始连接 URL: {}", discoveryUrl);
                    endpoints = DiscoveryClient.getEndpoints(discoveryUrl).get();
                }
                EndpointDescription endpoint = endpoints.stream()
                        .filter(e -> e.getSecurityPolicyUri().equals(securityPolicy.getUri()))
                        .filter(opcUaConfig.endpointFilter())
                        .findFirst()
                        .orElseThrow(() -> new Exception("没有连接上端点"));

                endpoint = OpcUAUtils.updateEndpointUrl(endpoint,server.getEndpointUrl());

                OpcUaClientConfig config = OpcUaClientConfig.builder()
                        .setApplicationName(LocalizedText.english("eclipse milo opc-ua client"))
                        .setApplicationUri("urn:eclipse:milo:examples:client")
                        .setCertificate(loader.getClientCertificate())
                        .setKeyPair(loader.getClientKeyPair())
                        .setEndpoint(endpoint)
                        //根据匿名验证和第三个用户名验证方式设置传入对象 AnonymousProvider（匿名方式）UsernameProvider（账户密码）
                        .setIdentityProvider(server.isAnonymousAccess() ? new AnonymousProvider() : new UsernameProvider(server.getUsername(),server.getPassword()))
                        .setRequestTimeout(uint(5000))
                        .build();

                log.info("使用端点: {} [{}/{}]", endpoint.getEndpointUrl(), securityPolicy, endpoint.getSecurityMode());
                opcUaClientList.add(OpcUaClient.create(config));
                //redisUtil.set(Constant.OPCUA_SERVER_STATUS + server.getEndpointUrl(), ServiceConnectionStatusEnum.CONNECTION_SUCCESS);
            } catch (Exception e) {
                //redisUtil.set(Constant.OPCUA_SERVER_STATUS + server.getEndpointUrl(), ServiceConnectionStatusEnum.CONNECTION_FAILED);
                log.error("[{}]客户端创建失败",server.toString());
                e.printStackTrace();
            }
        }
    }
}
