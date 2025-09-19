package com.hanweb.yyjr.zhangye.controller.ndrcontroller;

import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.JsonModelResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 发改委项目审核 Controller
 * @author xueyize
 * @since 2022-02-15
 */
@Controller
@RequestMapping(value = "interface/NDRC_xiangmushenhe")
public class NdrcXiangmushenheController {

    @Autowired
    com.hanweb.yyjr.zhangye.service.ndrcservice.NdrcXiangmushenheService ndrcXiangmushenheService;

    @RequestMapping("findAllData")
    @ResponseBody
    public String findAllData(String xmmc){
        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            if(StringUtil.isEmpty(xmmc)){
                return "必填参数为空";
            }
            param = ndrcXiangmushenheService.findAllData(xmmc);
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
