package com.io.agilefast.plugins.opcua.config;

import lombok.Data;
import org.apache.commons.lang.StringUtils;
//import org.apache.commons.lang3.StringUtils;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.function.Predicate;

/**
 * @Author Bernie
 * @Date 2020/8/26/026 13:47
 */
@Data
@ConfigurationProperties(prefix = "opcua")
public class OpcUaProperties {

    public List<OpcUaPropertie> servers;

    @Data
    public static class OpcUaPropertie {
        /**
         * 断点地址
         */
        private String endpointUrl;
        /**
         * 服务端句柄，每个服务地址唯一
         */
        private int clientHandle;
        /**
         * 用户名
         */
        private String username;
        /**
         * 密码
         */
        private String password;

        /**
         * 用户名和密码为空，则为匿名访问
         * @return StringUtils.isEmpty(username) && StringUtils.isEmpty(password)
         */
        public boolean isAnonymousAccess() {
            return StringUtils.isEmpty(username) && StringUtils.isEmpty(password);
        }

        public boolean equalEndpointUrl(String endpointUrl) {
            return this.endpointUrl.equals(endpointUrl);
        }
    }


    public Predicate<EndpointDescription> endpointFilter() {
        return e -> true;
    }
}
