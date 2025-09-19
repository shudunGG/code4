package com.hanweb.yyjr.zhangye.controller.weathercontroller;


import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.mvc.JsonModelResult;
import com.hanweb.yyjr.zhangye.service.weatherservice.WbFangLeiJianCeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 气象局防雷检测 Controller
 * @author xueyize
 * @since 2022-02-15
 */
@Controller
@RequestMapping(value = "interface/WB_fangleijiance")
public class WbFangLeiJianCeController {

    @Autowired
    WbFangLeiJianCeService wbFangleijianceService;

    @RequestMapping("findAllData")
    @ResponseBody
    public String findAllData(String xmmc){
        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            param = wbFangleijianceService.findAllData(xmmc);
        }catch (Exception e){
            e.printStackTrace();
            e.printStackTrace();

            result.setMessage("数据错误");
            result.setData("");
            result.setSuccess(false);
            JsonUtil.objectToString(result);
        }
        return   JsonUtil.objectToString(param);
    }
}
