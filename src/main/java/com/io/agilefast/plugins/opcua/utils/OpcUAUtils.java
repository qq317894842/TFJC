package com.io.agilefast.plugins.opcua.utils;

//import org.apache.commons.collections.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.nodes.Node;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;

import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @Author Bernie
 * @Date 2020/8/31/031 13:36
 */
public class OpcUAUtils {

    /**
     * 通过指定的opcUAUrl 获取对应的节点
     * @param client opcUAclient
     * @param opcUAUrl opcUAUrl
     * @return Node
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static Node getOpcUANode(OpcUaClient client, String opcUAUrl) throws ExecutionException, InterruptedException {
        String[] opcUAValue = opcUAUrl.split("\\.");
        // 首先获取地址对应的根节点
        List<Node> nodes = client.getAddressSpace().browse(Identifiers.RootFolder).get();
        for (Node node : nodes) {
          String ss=  node.getBrowseName().get().getName();

            if (opcUAValue[0].equals(node.getBrowseName().get().getName())) {
                // 递归遍历获取节点信息
                opcUAUrl = opcUAUrl.substring(opcUAUrl.indexOf(".") + 1);
                return getOpcUANode(client, node,opcUAUrl,opcUAValue.length,2);
            }
        }

        return null;
    }
    /**
     * 设置节点信息
     * */
//    public static Node setOpcUANode(OpcUaClient client,String opcUAUrl,String value){
//
//        String[] opcUAValue = opcUAUrl.split("\\.");
//        // 首先获取地址对应的根节点
//        List<Node> nodes = client.getAddressSpace().browse(Identifiers.RootFolder).get();
//        for (Node node : nodes) {
//            String ss=  node.getBrowseName().get().getName();
//
//            if (opcUAValue[0].equals(node.getBrowseName().get().getName())) {
//                // 递归遍历获取节点信息
//                opcUAUrl = opcUAUrl.substring(opcUAUrl.indexOf(".") + 1);
//                return getOpcUANode(client, node,opcUAUrl,opcUAValue.length,2);
//            }
//        }
//        return null;
//    }

    /**
     *  解析节点信息
     * @param client  opcUAclient
     * @param node Node
     * @param opcUAUrl opcUAUrl
     * @param startSize
     * @param size
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static Node getOpcUANode(OpcUaClient client, Node node, String opcUAUrl, int startSize, int size) throws ExecutionException, InterruptedException {
        List<Node> nodes = client.getAddressSpace().browseNode(node).get();

        String nodeValue = size == startSize ? opcUAUrl : opcUAUrl.substring(0, opcUAUrl.indexOf("."));
        opcUAUrl = opcUAUrl.substring(opcUAUrl.indexOf(".") + 1);

        Node result = null;
        if (CollectionUtils.isNotEmpty(nodes)) {
            for (Node node1 : nodes) {
                if (nodeValue.equals(node1.getBrowseName().get().getName())) {
                    if (size == startSize) return node1;
                    size ++;
                    result = getOpcUANode(client, node1, opcUAUrl, startSize, size);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 替换解析到的EndpointUrl为配置的ip地址
     * @param original 解析到的EndpointDescription
     * @param hostname 要替换的host
     * @return
     * @throws URISyntaxException
     */
    public static EndpointDescription updateEndpointUrl(
            EndpointDescription original, String hostname) throws URISyntaxException {

        /*URI uri = new URI(original.getEndpointUrl()).parseServerAuthority();

        String endpointUrl = String.format(
                "%s://%s:%s%s",
                uri.getScheme(),
                hostname,
                uri.getPort(),
                uri.getPath()
        );*/

        return new EndpointDescription(
                hostname,
                original.getServer(),
                original.getServerCertificate(),
                original.getSecurityMode(),
                original.getSecurityPolicyUri(),
                original.getUserIdentityTokens(),
                original.getTransportProfileUri(),
                original.getSecurityLevel()
        );
    }
}
