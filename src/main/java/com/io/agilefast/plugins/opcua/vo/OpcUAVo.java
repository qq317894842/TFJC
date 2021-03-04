package com.io.agilefast.plugins.opcua.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author Bernie
 * @Date 2020/8/31/031 11:37
 */
@ApiModel("OpcUAVo")
@Data
public class OpcUAVo {
    @ApiModelProperty(value = "opcUA标记")
    private String signName;

    @ApiModelProperty(value = "opcUA的属性地址url", example = "Objects.StaticData.StaticVariables.Boolean")
    private String opcUAUrl;

    @ApiModelProperty(value = "opcUA的属性值")
    private String value;

    @ApiModelProperty(value = "opcUA列表")
    private List<OpcUAVo> opcUAVoList;
}
