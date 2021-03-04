package com.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备清单
 *
 * @author alpha
 * @email daixirui@gmail.com
 * @date 2020-06-10 13:49:08
 */
@TableName("BPM_EM_EquipmentList")
public class BpmEmEquipmentlistEntity  implements Serializable {
	private static final long serialVersionUID = 1L;


	/**
	 * 设备编号
	 */
	@TableId(type = IdType.UUID)

	@ApiModelProperty(value = "设备编号")
	@NotBlank(message="设备编号不能为空")
	private String equipmentno;
	/**
	 * 设备编码
	 */

	@ApiModelProperty(value = "设备编码")
	@NotBlank(message="设备编码不能为空")
	private String equipmentcode;
	/**
	 * 设备名称
	 */

	private String equipmentname;
	/**
	 * 设备分类编码
	 */
	private String equipmentclassifycode;
	/**
	 * 设备分类名称
	 */

	@TableField(exist = false)
	private String equipmentclassifyname;
	/**
	 * 设备位置编码
	 */

	@ApiModelProperty(value = "设备位置编码")
	@NotBlank(message="设备位置编码不能为空")
	private String equlocationcode;
	/**
	 * 设备位置名称
	 */

	@TableField(exist = false)
	private String equlocationname;
	/**
	 * 设备位置序号
	 */
	private Integer equlocationseq;
	/**
	 * 设备巡检序号
	 */
	@TableField(exist=false)
	private Integer equmaintainseq;
	/**
	 * 品牌编码
	 */
	private String brandcode;
	/**
	 * 品牌名称
	 */

	@TableField(exist = false)
	private String brandname;
	/**
	 * 制造商编码
	 */

	private String manufacturercode;
	/**
	 * 制造商名称
	 */
	@TableField(exist = false)
	private String manufacturername;
	/**
	 * 供应商编码
	 */
	private String suppliercode;
	/**
	 * 供应商名称
	 */

	@TableField(exist = false)
	private String suppliername;
	/**
	 * 价格
	 */

	private BigDecimal price;
	/**
	 * 生产批次
	 */

	@ApiModelProperty(value = "生产批次")
	private String batch;
	/**
	 * 生产日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@ApiModelProperty(value = "生产日期")
	@NotNull(message="生产日期不能为空")
	private Date mfg;
	/**
	 * 保质期
	 */

	@ApiModelProperty(value = "保质期")
	@NotNull(message="保质期不能为空")
	private Integer sl;
	/**
	 * 开始有效期(Mfg/SL)
	 */

	@ApiModelProperty(value = "开始有效期")
	@NotNull(message="开始有效期不能为空")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date expstart;
	/**
	 * 有效期
	 */

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date exp;
	/**
	 * 设备状态
	 */

	private String opcuaurl;
	/**
	 * 启用时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date usetime;
	/**
	 * 结束时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endtime;
	/**
	 * 运行时长 单位：小时，可根据有效期做设备状态预警
	 */
	private Double runtime;
	/**
	 * 设备状态
	 */

	private String states;
	/**
	 * 设备状态描述
	 */
	private String statesname;
	/**
	 * 是否可用
	 */
	@ApiModelProperty(value = "是否可用")
	private Boolean isenable;
	/**
	 * 描述
	 */

	private String descriptions;
	/**
	 * 创建人
	 */
	private String createby;
	/**
	 * 创建人姓名
	 */

	private String createbyname;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")

	private Date createtime;
	/**
	 * 修改人
	 */
	private String modifyby;
	/**
	 * 修改人姓名
	 */
	private String modifybyname;
	/**
	 * 修改时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
	private Date modifytime;
	/**
	 * 设备运行状态
	 */
	private int runstatus;
	/**
	 * 设备状态更改时间
	 */
	private Date modifystatustime;

	public boolean getIsStatusReport() {
		return isstatusreport;
	}

	public void setIsStatusReport(boolean report) {
		isstatusreport = report;
	}

	/**
	 * 是否上报不可用
	 */
	private boolean isstatusreport;

	public Date getStatusReportTime() {
		return statusreporttime;
	}

	public void setStatusReportTime(Date statusReportTime) {
		statusreporttime = statusReportTime;
	}

	/**
	 * 上报时间
	 */
	private Date statusreporttime;

	/**
	 * 预留字段3
	 */
	private String userExtValue3;
	private String userExtValue1;
	private String userExtValue2;

	public String getUserExtValue1() {
		return userExtValue1;
	}

	public void setUserExtValue1(String userExtValue1) {
		this.userExtValue1 = userExtValue1;
	}

	public String getUserExtValue2() {
		return userExtValue2;
	}

	public void setUserExtValue2(String userExtValue2) {
		this.userExtValue2 = userExtValue2;
	}

	/**
	 * 预留字段4
	 */
	private String userExtValue4;
	/**
	 * 预留字段5
	 */
	private String userExtValue5;

	@TableField(exist = false)
	private String icon;
	/**
	 * 范德兰设备编号
	 */
	private String vanDerLandCode;

	@TableField(exist = false)
	private List<String> equlocationcodes;

	public List<String> getEqulocationcodes() {
		return equlocationcodes;
	}

	public void setEqulocationcodes(List<String> equlocationcodes) {
		this.equlocationcodes = equlocationcodes;
	}


	@TableField(exist = false)
	private BpmEmEquipmentinfoEntity equipmentinfoEntity;

	public BpmEmEquipmentinfoEntity getequipmentinfoEntity() {
		return equipmentinfoEntity;
	}

	public void setequipmentinfoEntity(BpmEmEquipmentinfoEntity equipmentinfoEntity) {
		this.equipmentinfoEntity = equipmentinfoEntity;
	}

	/**
	 * 设置：菜单图标
	 * @param icon 菜单图标
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * 设置：设备编号
	 */
	public void setEquipmentno(String equipmentno) {
		this.equipmentno = equipmentno;
	}
	/**
	 * 获取：设备编号
	 */
	public String getEquipmentno() {
		return equipmentno;
	}
	/**
	 * 设置：设备编码
	 */
	public void setEquipmentcode(String equipmentcode) {
		this.equipmentcode = equipmentcode;
	}
	/**
	 * 获取：设备编码
	 */
	public String getEquipmentcode() {
		return equipmentcode;
	}
	/**
	 * 设置：设备名称
	 */
	public void setEquipmentname(String equipmentname) {
		this.equipmentname = equipmentname;
	}
	/**
	 * 获取：设备名称
	 */
	public String getEquipmentname() {
		return equipmentname;
	}
	/**
	 * 设置：设备分类编码
	 */
	public void setEquipmentclassifycode(String equipmentclassifycode) {
		this.equipmentclassifycode = equipmentclassifycode;
	}
	/**
	 * 获取：设备分类编码
	 */
	public String getEquipmentclassifycode() {
		return equipmentclassifycode;
	}
	/**
	 * 设置：设备位置编码
	 */
	public void setEqulocationcode(String equlocationcode) {
		this.equlocationcode = equlocationcode;
	}
	/**
	 * 获取：设备位置编码
	 */
	public String getEqulocationcode() {
		return equlocationcode;
	}
	/**
	 * 设置：设备位置序号
	 */
	public void setEqulocationseq(Integer equlocationseq) {
		this.equlocationseq = equlocationseq;
	}
	/**
	 * 获取：设备位置序号
	 */
	public Integer getEqulocationseq() {
		return equlocationseq;
	}
	/**
	 * 设置：设备位置序号
	 */
	public void setequmaintainseq(Integer equmaintainseq) {
		this.equmaintainseq = equmaintainseq;
	}
	/**
	 * 获取：设备位置序号
	 */
	public Integer getequmaintainseq() {
		return equmaintainseq;
	}
	/**
	 * 设置：品牌编码
	 */
	public void setBrandcode(String brandcode) {
		this.brandcode = brandcode;
	}
	/**
	 * 获取：品牌编码
	 */
	public String getBrandcode() {
		return brandcode;
	}
	/**
	 * 设置：制造商编码
	 */
	public void setManufacturercode(String manufacturercode) {
		this.manufacturercode = manufacturercode;
	}
	/**
	 * 获取：制造商编码
	 */
	public String getManufacturercode() {
		return manufacturercode;
	}
	/**
	 * 设置：供应商编码
	 */
	public void setSuppliercode(String suppliercode) {
		this.suppliercode = suppliercode;
	}
	/**
	 * 获取：供应商编码
	 */
	public String getSuppliercode() {
		return suppliercode;
	}
	/**
	 * 设置：价格
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取：价格
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 设置：生产批次
	 */
	public void setBatch(String batch) {
		this.batch = batch;
	}
	/**
	 * 获取：生产批次
	 */
	public String getBatch() {
		return batch;
	}
	/**
	 * 设置：生产日期
	 */
	public void setMfg(Date mfg) {
		this.mfg = mfg;
	}
	/**
	 * 获取：生产日期
	 */
	public Date getMfg() {
		return mfg;
	}
	/**
	 * 设置：保质期
	 */
	public void setSl(Integer sl) {
		this.sl = sl;
	}
	/**
	 * 获取：保质期
	 */
	public Integer getSl() {
		return sl;
	}
	/**
	 * 设置：开始有效期(Mfg/SL)
	 */
	public void setExpstart(Date expstart) {
		this.expstart = expstart;
	}
	/**
	 * 获取：开始有效期(Mfg/SL)
	 */
	public Date getExpstart() {
		return expstart;
	}
	/**
	 * 设置：有效期
	 */
	public void setExp(Date exp) {
		this.exp = exp;
	}
	/**
	 * 获取：有效期
	 */
	public Date getExp() {
		return exp;
	}
	public void setOpcuaurl(String opcuaurl) {
		this.opcuaurl = opcuaurl;
	}
	public String getOpcuaurl() {
		return opcuaurl;
	}
	/**
	 * 设置：启用时间
	 */
	public void setUsetime(Date usetime) {
		this.usetime = usetime;
	}
	/**
	 * 获取：启用时间
	 */
	public Date getUsetime() {
		return usetime;
	}
	/**
	 * 设置：运行时长 单位：小时，可根据有效期做设备状态预警
	 */
	public void setRuntime(Double runtime) {
		this.runtime = runtime;
	}
	/**
	 * 获取：运行时长 单位：小时，可根据有效期做设备状态预警
	 */
	public Double getRuntime() {
		return runtime;
	}
	/**
	 * 设置：设备状态
	 */
	public void setStates(String states) {
		this.states = states;
	}
	/**
	 * 获取：设备状态
	 */
	public String getStates() {
		return states;
	}
	/**
	 * 设置：设备状态描述
	 */
	public void setStatesname(String statesname) {
		this.statesname = statesname;
	}
	/**
	 * 获取：设备状态描述
	 */
	public String getStatesname() {
		return statesname;
	}
	/**
	 * 设置：是否可用
	 */
	public void setIsenable(Boolean isenable) {
		this.isenable = isenable;
	}
	/**
	 * 获取：是否可用
	 */
	public Boolean getIsenable() {
		return isenable;
	}
	/**
	 * 设置：描述
	 */
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}
	/**
	 * 获取：描述
	 */
	public String getDescriptions() {
		return descriptions;
	}
	/**
	 * 设置：创建人
	 */
	public void setCreateby(String createby) {
		this.createby = createby;
	}
	/**
	 * 获取：创建人
	 */
	public String getCreateby() {
		return createby;
	}
	/**
	 * 设置：创建人姓名
	 */
	public void setCreatebyname(String createbyname) {
		this.createbyname = createbyname;
	}
	/**
	 * 获取：创建人姓名
	 */
	public String getCreatebyname() {
		return createbyname;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreatetime() {
		return createtime;
	}
	/**
	 * 设置：修改人
	 */
	public void setModifyby(String modifyby) {
		this.modifyby = modifyby;
	}
	/**
	 * 获取：修改人
	 */
	public String getModifyby() {
		return modifyby;
	}
	/**
	 * 设置：修改人姓名
	 */
	public void setModifybyname(String modifybyname) {
		this.modifybyname = modifybyname;
	}
	/**
	 * 获取：修改人姓名
	 */
	public String getModifybyname() {
		return modifybyname;
	}
	/**
	 * 设置：修改时间
	 */
	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getModifytime() {
		return modifytime;
	}

	public int getRunStatus() {
		return runstatus;
	}

	public void setRunStatus(int runStatus) {
		runstatus = runStatus;
	}

	public Date getModifyStatusTime() {
		return modifystatustime;
	}

	public void setModifyStatusTime(Date modifyStatusTime) {
		modifystatustime = modifyStatusTime;
	}

	/**
	 * 设置：预留字段3
	 */
	public void setUserExtValue3(String userExtValue3) {
		this.userExtValue3 = userExtValue3;
	}
	/**
	 * 获取：预留字段3
	 */
	public String getUserExtValue3() {
		return userExtValue3;
	}
	/**
	 * 设置：预留字段4
	 */
	public void setUserExtValue4(String userExtValue4) {
		this.userExtValue4 = userExtValue4;
	}
	/**
	 * 获取：预留字段4
	 */
	public String getUserExtValue4() {
		return userExtValue4;
	}
	/**
	 * 设置：预留字段5
	 */
	public void setUserExtValue5(String userExtValue5) {
		this.userExtValue5 = userExtValue5;
	}
	/**
	 * 获取：预留字段5
	 */
	public String getUserExtValue5() {
		return userExtValue5;
	}




	public void setequipmentclassifyname(String equipmentclassifyname) {
		this.equipmentclassifyname = equipmentclassifyname;
	}
	public String getequipmentclassifyname() {
		return equipmentclassifyname;
	}

	public void setequlocationname(String equlocationname) {
		this.equlocationname = equlocationname;
	}
	public String getequlocationname() {
		return equlocationname;
	}

	public void setbrandname(String brandname) {
		this.brandname = brandname;
	}
	public String getbrandname() {
		return brandname;
	}

	public void setsuppliername(String suppliername) {
		this.suppliername = suppliername;
	}
	public String getsuppliername() {
		return suppliername;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public String getVanDerLandCode() {
		return vanDerLandCode;
	}

	public void setVanDerLandCode(String vanDerLandCode) {
		this.vanDerLandCode = vanDerLandCode;
	}
}
