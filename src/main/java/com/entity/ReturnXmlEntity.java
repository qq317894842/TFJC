package com.entity;

import com.utils.R;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
/**
 * 需要包装返回的部分xml
 * */
@Data
public class ReturnXmlEntity {
    String SNDR;
    String RCVR;
    int SEQN;
    String DDTM;
    String TYPE;
    String STYP;
    String MGID;
    String RMID;
    String APOT;
    int NUM;
    List<ReturnEquipmentEntity> xmlList;

    @Override
    public String toString() {
        StringBuffer sb=new StringBuffer();
        for (ReturnEquipmentEntity equipmentXml : xmlList) {
            sb.append(equipmentXml.toString());
        }
        SimpleDateFormat f=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return "<MSG>\n" +
                "<META>\n" +
                "<SNDR>BHS</SNDR>\n" +
                "<RCVR>"+RCVR+"</RCVR>\n" +
                "<SEQN>"+SEQN+"</SEQN>\n" +
                "<DDTM>"+ f.format( new Date())+"</DDTM>\n" +
                "<TYPE>BHSE</TYPE>\n" +
                "<STYP>BRDL</STYP>\n" +
                "<MGID>"+UUID.randomUUID()+"</MGID>\n" +
                "<RMID>"+RMID+"</RMID>\n" +
                "<APOT>ZUTF</APOT>\n" +
                "<SORC>BHS</SORC>\n" +
                "</META>\n" +
                "<NUM>"+(NUM<=0?"":NUM)+"</NUM>\n" +
                "<EQUIPMENTS>\n" +
                sb.toString()+
                "</EQUIPMENTS>\n" +
                "</MSG>";
    }
}
