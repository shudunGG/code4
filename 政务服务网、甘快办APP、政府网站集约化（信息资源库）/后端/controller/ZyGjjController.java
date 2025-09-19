package com.hanweb.yyjr.zhangye.controller;

import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.gsszf.bean.ReturnEnum;
import com.hanweb.yyjr.zhangye.service.ZyGjjService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


/**
 * @ClassName ZyGjjController
 * @Description 张掖市中转接口
 * @Author zds
 * @Date: 2022/3/8 14:42
 */
@Api(tags = "张掖市公积金接口")
@RestController
@RequestMapping("interface/zy-transfer")
public class ZyGjjController {

    @Autowired
    private ZyGjjService zyGjjService;


    /**
     * 张掖市公积金查询
     *
     * @param bodyParam 请求体json
     * @return
     */
    @ApiOperation(value = "张掖市公积金查询", notes = "张掖市公积金查询入口", httpMethod = "POST")
    @ApiImplicitParam(name = "bodyParam", value = "请求体json", required = true, paramType = "String")
    @PostMapping(value = "gjjQuery")
    public JsonResult gjjQuery(@RequestBody String bodyParam) {
        JsonResult result = JsonResult.getInstance();
        boolean mastParam = StringUtil.isNotEmpty(bodyParam);
        if (Boolean.FALSE.equals(mastParam)) {
            result.setCode(ReturnEnum.RT400.getCode()).setSuccess(ReturnEnum.RT400.isSuccess())
                    .setMessage(ReturnEnum.RT400.getMsg());
            return result;
        }
        return zyGjjService.execute(result, bodyParam);
    }



    //@RequestMapping(value = "gjjDownLoad")
    /**
     * 公积金职工缴存证明和异地贷款缴存证明下载
     * @param bodyParam 请求体json
     * @param response
     * @return
     */
    public Object gjjDownLoad(String bodyParam, HttpServletResponse response) {
        JsonResult result = JsonResult.getInstance();
        boolean mastParam = StringUtil.isNotEmpty(bodyParam);
        if (Boolean.FALSE.equals(mastParam)) {
            result.setCode(ReturnEnum.RT400.getCode()).setSuccess(ReturnEnum.RT400.isSuccess())
                    .setMessage(ReturnEnum.RT400.getMsg());
            return result;
        }
        return zyGjjService.downLoadProve(result, bodyParam, response);
    }


}
