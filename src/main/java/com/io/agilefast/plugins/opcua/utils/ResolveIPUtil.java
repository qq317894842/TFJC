package com.io.agilefast.plugins.opcua.utils;

import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author Bernie
 * @Date 2020/9/9/009 15:52
 */
public class ResolveIPUtil {
    @Data
    public static class IPAttribute {
        private String ip;
        private Integer port;
    }

    public static IPAttribute getIPAttribute(String url) {
        Pattern p = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+)\\:(\\d+)");
        Matcher m = p.matcher(url);
        IPAttribute attribute = new IPAttribute();

        //将符合规则的提取出来
        while (m.find()) {
            attribute.setIp(m.group(1));
            attribute.setPort(Integer.parseInt(m.group(2)));
        }

        return attribute;
    }
}
