package com.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.entity.BpmEmEquipmentlistEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 设备清单
 *
 * @author alpha
 * @email daixirui@gmail.com
 * @date 2020-06-10 13:49:08
 */

public interface BpmEmEquipmentlistDao extends BaseMapper<BpmEmEquipmentlistEntity> {

    @Select("select * from BPM_EM_EquipmentList")
    List<BpmEmEquipmentlistEntity> getAllEquipmentList();
    IPage<BpmEmEquipmentlistEntity> queryListPage(IPage<BpmEmEquipmentlistEntity> page,@Param("ew") QueryWrapper<BpmEmEquipmentlistEntity> in);
    IPage<BpmEmEquipmentlistEntity> queryListPage2(IPage<BpmEmEquipmentlistEntity> page,@Param("ew") QueryWrapper<BpmEmEquipmentlistEntity> in);
    IPage<BpmEmEquipmentlistEntity> queryPageByArea(IPage<BpmEmEquipmentlistEntity> page,@Param("ew") QueryWrapper<BpmEmEquipmentlistEntity> in);

    @Select("select el.*,ec.equmaintainseq from BPM_EM_EquipmentCharge as ec\n" +
            "inner join BPM_EM_EquipmentChargeTeam as ect on ec.ChargeTeam=ect.EquChargeTeamCode\n" +
            "inner join BPM_EM_EquipmentList as el on ec.EquipmentCode=el.EquipmentNo\n" +
            "where ec.ChargeTeam= #{ChargeTeam}")
    List<BpmEmEquipmentlistEntity> getListByEquChargeTeamCode(@Param("ChargeTeam")String ChargeTeam);

    @Select("select el.*,ea.equmaintainseq from BPM_EM_EquipmentArea as ea\n" +
            "inner join BPM_EM_EquipmentList as el on ea.EquipmentNo=el.EquipmentNo\n" +
            "where ea.AreaCode= #{AreaCode}")
    List<BpmEmEquipmentlistEntity> getListByAreaCode(@Param("AreaCode")String AreaCode);

    @Select("SELECT bee.*,bem.Executor as userExtValue1 from BPM_EM_EquipmentList bee,BMS_EM_MaintainPlanDetail bem where bee.EquipmentNo=bem.EquipmentNo and bem.PlanNo=#{planno}")
    List<BpmEmEquipmentlistEntity> getListAndEquipPlanDetail(@Param("planno")String planno);

    @Select("SELECT bee.*,bem.Executor as userExtValue1,bhs.name as userExtValue2 \n" +
            "from BPM_EM_EquipmentList bee\n" +
            "inner join BMS_EM_InspectionPlanDetail bem on bee.EquipmentNo=bem.EquipmentNo\n" +
            "left join BMS_HRM_StaffMainten bhs  on bhs.jobcode=bem.Executor\n" +
            "where bem.PlanNo=#{planno}")
    List<BpmEmEquipmentlistEntity> getListAndEquipInsPlanDetail(@Param("planno")String planno);

    @Select("SELECT * from BPM_EM_EquipmentList where EquipmentCode=#{EquipmentCode} and isnull(States,'')<>'报废'")
    List<BpmEmEquipmentlistEntity> getListByEquipmentCode(@Param("EquipmentCode")String EquipmentCode);

    @Select("SELECT * from BPM_EM_EquipmentList where isnull(States,'')<>'报废'")
    List<BpmEmEquipmentlistEntity> getListByState();

    List<BpmEmEquipmentlistEntity> queryList(Map<String, Object> params,@Param("ew") QueryWrapper queryWrapper);
}
