package com.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.caacitc.esb.client.EsbClient;
import com.caacitc.esb.dto.ProducerSendResult;
import com.entity.BpmEmEquipmentlistEntity;
import com.entity.OpcEquipmentEntity;
import com.service.BpmEmEquipmentlistService;
import com.utils.EquipmentUtils;
import com.utils.PageUtils;
import com.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *  提供给mmis访问的接口（设备状态为不可用时另外主动推送信息）
 * */
@Slf4j
@Api(tags = "OPCUA测试接口")
@RestController
@RequestMapping("/equipmentUnavailable")
public class EmEquipmentStatusController {

    @Autowired
    @Qualifier("BpmEmEquipmentlistServiceimpl")
    BpmEmEquipmentlistService bpmEmEquipmentlistService;

    @GetMapping("/sendUnMsg")
    @ApiOperation(value = "变更数据库并主动推送不可用消息", notes = "根据给定的equipmentno值操作数据库")
    public void sendUnMsg(String equipmentno){
        List<BpmEmEquipmentlistEntity> list=bpmEmEquipmentlistService.list(
                new QueryWrapper<BpmEmEquipmentlistEntity>().eq("EquipmentNo",equipmentno)
        );
        BpmEmEquipmentlistEntity entity=list.get(0);
        entity.setIsStatusReport(true); //更改是否上报
        int status=entity.getRunStatus();
        entity.setStatusReportTime(new Date());//更新上报时间
        boolean b = bpmEmEquipmentlistService.updateById(entity);
        if (b) {
            System.err.println("更改不可用是否上报信息失败");
        }
        //判断这个设备名称属于那一个种类
        String equipmentName1="转盘";
        String equipmentName2="称重输送机";
        String equipmentName3="安检机";
        String name=entity.getEquipmentname();
        if (name.indexOf(equipmentName1)>=0){
            entity.setEquipmentname(equipmentName1);
        }
        if (name.indexOf(equipmentName2)>=0){
            entity.setEquipmentname(equipmentName2);
        }
        if (name.indexOf(equipmentName3)>=0){
            entity.setEquipmentname(equipmentName3);
        }
        //将全局消息序号进行累加
        int count= EquipmentUtils.AllMsgNum;
        if (count==999999)count=0;
        count++;
        OpcEquipmentEntity opcEquipmentEntity=new OpcEquipmentEntity();
        opcEquipmentEntity.setEQUIPMENTNUMBER(entity.getEquipmentno());
        opcEquipmentEntity.setEQUIPMENTTYPE(entity.getEquipmentname()); //种类
        opcEquipmentEntity.setEQUIPMENTSTATUSNEW(entity.getRunStatus());
        opcEquipmentEntity.setEQUIPMENTTIME(entity.getModifyStatusTime());
        opcEquipmentEntity.setEQUIPMENTSTATUSOLD(status); //旧状态
        opcEquipmentEntity.setREASON(""); //问题信息
        opcEquipmentEntity.setSEQN(count);
        //包装成xml
        String sendStr=opcEquipmentEntity.toString();
        System.out.println("xml: \n"+sendStr);
        //通过这个主动发送消息到他们的jar包
        ProducerSendResult producer = EsbClient.getInstance.producer(sendStr);
        String sendState = producer.getSendState();
        System.out.println(sendState);
        String sendDesc = producer.getSendDesc();
        System.out.println(sendDesc);
        System.out.println("==> "+producer);

    }


    @PostMapping("bpmequipmentList/{pageNum}")
    @ApiOperation("获取清单列表")
    public R list1(@RequestBody Map<String, Object> params, @PathVariable("pageNum") int pageNum) {

        params.put("page", String.valueOf(pageNum));

        PageUtils page = bpmEmEquipmentlistService.queryListPage(params);

        return R.ok().put("page", page);
    }
}
