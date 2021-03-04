package com.entity;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * opc监测设备状态变化，返回的xml实体类
 * */
@Data
public class OpcEquipmentEntity {
    String SNDR;
    String RCVR;
    int SEQN;
    String DDTM;
    String TYPE;
    String STYP;
    String MGID;
    String APOT;
    String EQUIPMENTNUMBER; //设备编号
    String 	EQUIPMENTTYPE; //设备种类
    int EQUIPMENTSTATUSNEW; //设备当前状态
    Date EQUIPMENTTIME; //设备变更的状态时间
    int EQUIPMENTSTATUSOLD; //设备旧状态
    String REASON;  //故障原因

    @Override
    public String toString(){
        SimpleDateFormat f=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String time=f.format(new Date());
        return "<MSG>\n" +
                "<META>\n" +
                "<SNDR>BHS</SNDR>\n" +
                "<RCVR></RCVR>\n" +
                "<SEQN>"+SEQN+"</SEQN>\n" +
                "<DDTM>"+time+"</DDTM>\n" +
                "<TYPE>BHSE</TYPE>\n" +
                "<STYP>BRUE</STYP>\n" +
                "       <MGID>"+UUID.randomUUID()+"</MGID>\n" +
                "       <RMID></RMID>\n" +
                "       <APOT>ZUTF</APOT>\n" +
                "       <SORC>BHS</SORC>\n" +
                "</META>\n" +
                "<EQUIPMENT>\n" +
                "<EQUIPMENTNUMBER>"+EQUIPMENTNUMBER+"</EQUIPMENTNUMBER>\n" +
                "       <EQUIPMENTTYPE>"+EQUIPMENTTYPE+"</EQUIPMENTTYPE>\n" +
                "       <EQUIPMENTSTATUSOLD>"+EQUIPMENTSTATUSOLD+"</EQUIPMENTSTATUSOLD>\n" +
                "       <EQUIPMENTSTATUSNEW>"+EQUIPMENTSTATUSNEW+"</EQUIPMENTSTATUSNEW>\n" +
                "       <EQUIPMENTTIME>"+f.format(EQUIPMENTTIME)+"</EQUIPMENTTIME>\n" +
                "       <REASON>"+REASON+"</REASON>\n" +
                "   </EQUIPMENT>\n" +
                "</MSG>";
    }
}
