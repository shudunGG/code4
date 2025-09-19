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
 * 发改委行政许可 Controller
 * @author xueyize
 * @since 2022-02-15
 */
@Controller
@RequestMapping(value = "interface/NDRC_xingzhengxuke")
public class NdrcXingzhengxukeController {

    @Autowired
    com.hanweb.yyjr.zhangye.service.ndrcservice.NdrcXingzhengxukeService ndrcXingzhengxukeService;

    @RequestMapping("findxzxkjdwsmcList")
    @ResponseBody
    public String findxzxkjdwsmcList(String fdr,String fdrsfz){
        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            if(StringUtil.isEmpty(fdr) || StringUtil.isEmpty(fdrsfz)){
                return "必填参数为空";
            }
            param = ndrcXingzhengxukeService.findxzxkjdwsmcList(fdr,fdrsfz);
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
    public String findAllData(String xzxkjdwsmc){
        JsonModelResult result = JsonModelResult.getInstance();
        List<Map<String, Object>> param = new ArrayList<>();
        try{
            if(StringUtil.isEmpty(xzxkjdwsmc)){
                return "必填参数为空";
            }
            param = ndrcXingzhengxukeService.findAllData(xzxkjdwsmc);
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
