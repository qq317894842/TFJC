package com.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Auther: HuangRui
 * @Date: 2021/2/9 14:35
 * @Description: 设备数据
 */
@Data
public class Equipment {
    /**
     * 设备号
     */
    private String equipmentNumber;
    /**
     * 设备类型
     */
    private String equipmentType;

    /**
     * 设备状态，变更前状态
     */
    private String equipmentStatusOld;

    /**
     * 设备状态，变更后状态
     */
    private String equipmentStatusNew;

    /**
     * 状态变更时间
     */
    private LocalDateTime equipmentTime;

    /**
     * 原因
     */
    private String reason;

}
