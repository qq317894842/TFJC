package t;

//import Util.RabbitMqUtil;
import Util.WriteInFile;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TEST {
    public static void main(String[] args) {

        //发送一次就从1-999999累积叠加 到最大时清零使用
        SimpleDateFormat f=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String time=f.format(new Date());
        String str="<MSG>\n" +
                "    <META>\n" +
                "        <SNDR></SNDR>\n" +
                "        <RCVR>BHS</RCVR>\n" +
                "        <SEQN>"+1+"</SEQN>\n" +
                "        <DDTM>"+time+"</DDTM>\n" +
                "        <TYPE>BHSE</TYPE>\n" +
                "        <STYP>BRUE</STYP>\n" +
                "       <MGID>A20121220234816RE7A97855BA84fd5B</MGID>\n" +
                "       <RMID></RMID>\n" +
                "       <APOT>ZUTF</APOT>\n" +
                "    </META>\n" +
                "    <EQUIPMENT>\n" +
                "       <EQUIPMENTNUMBER>A01</EQUIPMENTNUMBER>\n" +
                "       <EQUIPMENTTYPE></EQUIPMENTTYPE>\n" +
                "       <EQUIPMENTSTATUSOLD>"+1+"</EQUIPMENTSTATUSOLD>\n" +
                "       <EQUIPMENTSTATUSNEW>"+2+"</EQUIPMENTSTATUSNEW>\n" +
                "       <EQUIPMENTTIME>2022-02-24 14:11:11.000</EQUIPMENTTIME>\n" +
                "       <REASON></REASON>\n" +
                "   </EQUIPMENT>\n" +
                "</MSG>";
        System.err.println(str);
//        RabbitMqUtil.postData("EQUIPMENT_REQE",str);
        WriteInFile.WriteFile(str,"EQUIPMENT_REQE");
    }
    @Test
    public void test1(){
        String a=Integer.toBinaryString(223);
        System.out.println(a);
    }

}
