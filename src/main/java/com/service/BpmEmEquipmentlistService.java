package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.entity.BpmEmEquipmentlistEntity;
import com.utils.PageUtils;
import com.utils.R;

import java.util.List;
import java.util.Map;

public interface BpmEmEquipmentlistService extends IService<BpmEmEquipmentlistEntity> {
    List<BpmEmEquipmentlistEntity> getListByOpcUAUrl(String OpcUAUrl);

//    R handlerVanDerlandInterface(String xml);
    PageUtils queryListPage(Map<String, Object> params);
}
