package com.hanweb.yyjr.zhangye.controller.cdpfcontroller;

import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.JsonModelResult;
import com.hanweb.gsszf.bean.ReturnEnum;
import com.hanweb.yyjr.zhangye.service.cdpfservice.CdpfChildrenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 残疾儿童康复基本信息 Controller
 * @author xueyize
 * @since 2022-02-15
 */
@Controller
@RequestMapping(value = "interface/CDPF_Children")
public class CdpfChildrenController {

    @Autowired
    CdpfChildrenService cdpfChildrenService;

    @RequestMapping("findAllData")
    @ResponseBody
    public String findAllData(String groupName){
        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            if(StringUtil.isEmpty(groupName)){
                return "必填参数为空";
            }
            param = cdpfChildrenService.findAllData(groupName);
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
