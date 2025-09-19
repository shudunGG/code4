package com.hanweb.yyjr.zhangye.controller.ndrcontroller;

import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.JsonModelResult;
import com.hanweb.gsszf.constant.StaticValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发改委权责清单 Controller
 * @author xueyize
 * @since 2022-02-15
 */
@Controller
@RequestMapping(value = "interface/NDRC_quanzeList")
public class NdrcQuanzeListController {

    @Autowired
    com.hanweb.yyjr.zhangye.service.ndrcservice.NdrcQuanzeListService ndrcQuanzeListService;

    @RequestMapping("findEventChildNameList")
    @ResponseBody
    public String findEventChildNameList(String eventChildName){
        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            if(StringUtil.isEmpty(eventChildName)){
                return "必填参数为空";
            }
            param = ndrcQuanzeListService.findEventChildNameList(eventChildName);
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
    public String findAllData(String eventChildName){
        JsonModelResult result = JsonModelResult.getInstance();
        Map<String, Object> param = new HashMap<>(StaticValues.NUM_16);
        try{
            if(StringUtil.isEmpty(eventChildName)){
                return "必填参数为空";
            }
            param = ndrcQuanzeListService.findAllData(eventChildName);
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
