package com.entity;

import lombok.Data;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
/**
 * 返回的具体设备状态数据
 * */
@Data
@ToString
public class ReturnEquipmentEntity {
    String EQUIPMENTNUMBER; //设备编号
    String 	EQUIPMENTTYPE; //设备种类
    int EQUIPMENTSTATUS; //设备状态
    Date EQUIPMENTTIME; //设备变更的状态时间

    @Override
    public String toString() {
        SimpleDateFormat f=new SimpleDateFormat("yyyyMMddHHmmssSSS");
              return "<EQUIPMENT>\n" +
                "<EQUIPMENTNUMBER>"+EQUIPMENTNUMBER+"</EQUIPMENTNUMBER>\n" +
                "<EQUIPMENTTYPE>"+EQUIPMENTTYPE+"</EQUIPMENTTYPE>\n" +
                "<EQUIPMENTSTATUS>"+EQUIPMENTSTATUS+"</EQUIPMENTSTATUS>\n" +
                "<EQUIPMENTTIME>"+f.format(EQUIPMENTTIME)+"</EQUIPMENTTIME> \n" +
                "</EQUIPMENT>";
    }
}
