package com.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dao.BpmEmEquipmentlistDao;
import com.entity.BpmEmEquipmentlistEntity;
import com.service.BpmEmEquipmentlistService;
import com.utils.PageUtils;
import com.utils.Query;
import com.utils.R;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service("BpmEmEquipmentlistServiceimpl")
public class BpmEmEquipmentlistServiceimpl extends ServiceImpl<BpmEmEquipmentlistDao, BpmEmEquipmentlistEntity> implements BpmEmEquipmentlistService {
    @Override
    public List<BpmEmEquipmentlistEntity> getListByOpcUAUrl(String OpcUAUrl) {

        return this.list(
                new QueryWrapper<BpmEmEquipmentlistEntity>()
                        .eq("OpcUAUrl", OpcUAUrl));
    }
    @Override
    public PageUtils queryListPage(Map<String, Object> params) {
        String equipmentno = String.valueOf(params.get("equipmentno"));
        String equipmentcode = String.valueOf(params.get("equipmentcode"));
        String equipmentname = String.valueOf(params.get("equipmentname"));
        String equipmentclassifycode = String.valueOf(params.get("equipmentclassifycode"));
        List<String> equlocationcodeslist = (List<String>) params.get("equlocationcodes");

        IPage<BpmEmEquipmentlistEntity> page = this.baseMapper.queryListPage(
                new Query<BpmEmEquipmentlistEntity>(params).getPage(),
                new QueryWrapper<BpmEmEquipmentlistEntity>()
                        .like(StringUtils.isNotBlank(equipmentno), "eel.EquipmentNo", equipmentno)
                        .like(StringUtils.isNotBlank(equipmentcode), "eel.EquipmentCode", equipmentcode)
                        .like(StringUtils.isNotBlank(equipmentname), "eel.EquipmentName", equipmentname)
                        .like(StringUtils.isNotBlank(equipmentclassifycode), "eei.EquipmentClassifyCode", equipmentclassifycode)
                        .in(!equlocationcodeslist.isEmpty(), "eel.EquLocationCode", equlocationcodeslist)
                        .eq("RunStatus",0) //所有状态为不可用0的数据
        );

        return new PageUtils(page);
    }

}
