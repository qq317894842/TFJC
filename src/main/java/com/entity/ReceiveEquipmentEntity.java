package com.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author Bernie
 * @Date 2020/7/21 20:50
 * 解析xml包装的实体类
 */
@Data
public class ReceiveEquipmentEntity {
    /**
     * 消息创建时的时间戳
     */
//    Date messageTime;
    /**
     * 发送消息的组件实例
     */
    String SNDR;
    String RCVR;
    String SEQN;
    String DDTM;
    String TYPE;
    String STYP;
    String MGID;
    String RMID;
    String APOT;

    List<String> EQUIPMENTNUMBER;






}
