package com.controller;

import com.utils.EquipmentUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: HuangRui
 * @Date: 2021/2/19 15:22
 * @Description:
 */
//@RequestMapping("/tfjc")
//@RestController
public class TfjcController {

    @RequestMapping("/getJson")
    public String getJson(){
        String jsonStr = EquipmentUtils.getJsonStr();
        System.out.println(jsonStr);
        return jsonStr;
    }

}
