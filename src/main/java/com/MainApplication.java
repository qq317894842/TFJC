package com;

//import com.aliyun.openservices.shade.com.alibaba.fastjson.JSONArray;
import com.utils.EquipmentUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @Auther: HuangRui
 * @Date: 2021/2/9 13:30
 * @Description:
 */
public class MainApplication {
//    public static void main(String[] args) {
//        //模拟从MMIS返回的json数据
//        String url = "http://39.108.5.84:8088/test/getJson";
//        String jsonStr = MainApplication.doGet(url);
//        System.out.println("获取的json： \n"+jsonStr);
//        //封装<EQUIPMENT>数据
//        String equipMentXML = EquipmentUtils.parseEquipMentXML(JSONArray.parseArray(jsonStr));
//        //<meta>标签信息
//        String xml = EquipmentUtils.packageXml(equipMentXML);
//        System.out.println("转化后的xml：\n"+xml);
//
//    }

    public static String doGet(String url){
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        HttpGet httpGet = new HttpGet(url);

        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                String content = EntityUtils.toString(responseEntity);
//                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                System.out.println("响应内容长度为:" + content.length());
                System.out.println("响应内容为:" + content);
                return  content;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
