package com.hanweb.yyjr.zhangye.controller.spadcontroller;

import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.JsonModelResult;
import com.hanweb.gsszf.constant.StaticValues;
import com.hanweb.yyjr.zhangye.service.spadservice.SpadBridgeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 桥梁信息 Controller
 * @author xueyize
 * @since 2022-02-15
 */
@Controller
@RequestMapping(value = "interface/SPAD_bridgeInfo")
public class SpadBridgeInfoController {

    @Autowired
    SpadBridgeInfoService spadBridgeInfoService;

    @RequestMapping("findBridgeNameList")
    @ResponseBody
    public String findBridgeNameList(String bridgename){
        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            if(StringUtil.isEmpty(bridgename)){
                return "必填参数为空";
            }
            param = spadBridgeInfoService.findBridgeNameList(bridgename);
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

    @RequestMapping("findAllData")
    @ResponseBody
    public String findAllData(String bridgename){
        JsonModelResult result = JsonModelResult.getInstance();
        Map<String, Object> param = new HashMap<>(StaticValues.NUM_16);
        try{
            if(StringUtil.isEmpty(bridgename)){
                return "必填参数为空";
            }
            param = spadBridgeInfoService.findAllData(bridgename);
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
