package com.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备档案
 *
 * @author alpha
 * @email daixirui@gmail.com
 * @date 2020-06-10 13:49:08
 */
@TableName("BPM_EM_EquipmentInfo")
public class BpmEmEquipmentinfoEntity  implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 设备编码
	 */
	@TableId
	@ApiModelProperty(value = "设备编码")
	@NotBlank(message="设备编码不能为空")
	private String equipmentcode;
	/**
	 * 设备名称
	 */
	@ApiModelProperty(value = "设备名称")
	@NotBlank(message="设备名称不能为空")
	private String equipmentname;
	/**
	 * 设备分类编码
	 */

	@ApiModelProperty(value = "设备分类编码")
	@NotBlank(message="设备分类编码不能为空")
	private String equipmentclassifycode;


	@TableField(exist = false)
	private String classifyname;
	/**
	 * 单位
	 */

	@ApiModelProperty(value = "单位编码")
	@NotBlank(message="单位编码不能为空")
	private String unit;

	@TableField(exist = false)

	private String unitname;

	/**
	 * 品牌编码
	 */

	@ApiModelProperty(value = "品牌编码")
	private String brandcode;

	@TableField(exist = false)

	private String brandname;

	/**
	 * 默认供应商编码
	 */

	@ApiModelProperty(value = "默认供应商编码")
	private String suppliercode;

	@TableField(exist = false)

	private String suppliername;

	/**
	 * 制造商
	 */

	@ApiModelProperty(value = "制造商")
	private String manufacturercode;

	/**
	 * 管理方式编码
	 */

	@ApiModelProperty(value = "管理方式编码")
	private String supervisormodecode;

	@TableField(exist = false)

	private String supervisormodename;

	/**
	 * 等级编码
	 */

	@ApiModelProperty(value = "等级编码")
	private String gradecode;

	/**
	 * 等级名称
	 */
	@TableField(exist = false)

	private String gradename;


	/**
	 * 单价
	 */

	@ApiModelProperty(value = "单价")
	private BigDecimal price;
	/**
	 * 规格
	 */

	@ApiModelProperty(value = "规格")
	private String specification;
	/**
	 * 型号
	 */

	@ApiModelProperty(value = "型号")
	private String modelno;
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")

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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date modifytime;
	/**
	 * 预留字段1
	 */
	private String userExtValue1;
	/**
	 * 预留字段2
	 */
	private String userExtValue2;
	/**
	 * 预留字段3
	 */
	private String userExtValue3;
	/**
	 * 预留字段4
	 */
	private String userExtValue4;
	/**
	 * 预留字段5
	 */
	private String userExtValue5;

//	//设备维护周期
//	@TableField(exist = false)
//	private List<BpmEmEquipmentmaintenancecycleEntity> equipmentmaintenancecycleEntityList;

	public String getGradename() {
		return gradename;
	}

	public void setGradename(String gradename) {
		this.gradename = gradename;
	}

	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}

	public String getSupervisormodename() {
		return supervisormodename;
	}

	public void setSupervisormodename(String supervisormodename) {
		this.supervisormodename = supervisormodename;
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public String getUnitname() {
		return unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

//	public List<BpmEmEquipmentmaintenancecycleEntity> getequipmentmaintenancecycleEntityList() {
//		return equipmentmaintenancecycleEntityList;
//	}

	public String getClassifyname() {
		return classifyname;
	}

	public void setClassifyname(String classifyname) {
		this.classifyname = classifyname;
	}

//	public void setequipmentmaintenancecycleEntityList(List<BpmEmEquipmentmaintenancecycleEntity> equipmentmaintenancecycleEntityList) {
//		this.equipmentmaintenancecycleEntityList = equipmentmaintenancecycleEntityList;
//	}


//	//设备巡检周期
//	@TableField(exist = false)
//	private List<BpmEmEquipmentpqccycleEntity> equipmentpqccycleEntityList;
//	public List<BpmEmEquipmentpqccycleEntity> getequipmentpqccycleEntityList() {
//		return equipmentpqccycleEntityList;
//	}

//	public void setequipmentpqccycleEntityList(List<BpmEmEquipmentpqccycleEntity> equipmentpqccycleEntityList) {
//		this.equipmentpqccycleEntityList = equipmentpqccycleEntityList;
//	}


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
	 * 设置：单位
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * 获取：单位
	 */
	public String getUnit() {
		return unit;
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
	 * 设置：默认供应商编码
	 */
	public void setSuppliercode(String suppliercode) {
		this.suppliercode = suppliercode;
	}
	/**
	 * 获取：默认供应商编码
	 */
	public String getSuppliercode() {
		return suppliercode;
	}
	/**
	 * 设置：制造商
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
	 * 设置：管理方式编码
	 */
	public void setSupervisormodecode(String supervisormodecode) {
		this.supervisormodecode = supervisormodecode;
	}
	/**
	 * 获取：管理方式编码
	 */
	public String getSupervisormodecode() {
		return supervisormodecode;
	}
	/**
	 * 设置：等级编码
	 */
	public void setGradecode(String gradecode) {
		this.gradecode = gradecode;
	}
	/**
	 * 获取：等级编码
	 */
	public String getGradecode() {
		return gradecode;
	}
	/**
	 * 设置：单价
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取：单价
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 设置：规格
	 */
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	/**
	 * 获取：规格
	 */
	public String getSpecification() {
		return specification;
	}
	/**
	 * 设置：型号
	 */
	public void setModelno(String modelno) {
		this.modelno = modelno;
	}
	/**
	 * 获取：型号
	 */
	public String getModelno() {
		return modelno;
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
	/**
	 * 设置：预留字段1
	 * 种类 species
	 */
	public void setUserExtValue1(String userExtValue1) {
		this.userExtValue1 = userExtValue1;
	}
	/**
	 * 获取：预留字段1
	 */
	public String getUserExtValue1() {
		return userExtValue1;
	}
	/**
	 * 设置：预留字段2
	 */
	public void setUserExtValue2(String userExtValue2) {
		this.userExtValue2 = userExtValue2;
	}
	/**
	 * 获取：预留字段2
	 */
	public String getUserExtValue2() {
		return userExtValue2;
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
}
