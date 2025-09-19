package com.hanweb.yyjr.zhangye.controller;

import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.log.Log;
import com.hanweb.common.util.log.LogFactory;
import com.hanweb.common.util.mvc.JsonModelResult;
import com.hanweb.complat.exception.OperationException;

import com.hanweb.gsszf.constant.StaticValues;
import com.hanweb.yyjr.zhangye.service.HtcxService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @className: SjcxController
 * @description: 合同查询接口对接
 * @author: freedom
 * @date: 2024/09/02 15:49
 * @Company: Copyright© 2024 DaHan Inc.
 **/
@Api(tags = "合同查询接口对接")
@RestController
@RequestMapping("interface/query")
public class HtcxController {

    private static final Log LOGGER = LogFactory.getInstance(HtcxController.class);

    @Resource
    private HtcxService htcxService;

    /*
     * 商品房买卖合同查询接口
     *
     * @param 
     * @return 
     * @author Ma
     * @since 2024/9/2 16:42
     */
    @RequestMapping("/newHouseContract")
    public JsonModelResult newHouseContract(String params){
        JsonModelResult result = JsonModelResult.getInstance();
        result.setCode(StaticValues.STR_500).setSuccess(false).setMessage(StaticValues.FALSE_MSG);
        String data = null;
        try {
            data = htcxService.newHouseContract(params);
        } catch (OperationException e) {
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("商品房买卖合同查询失败：" + e);
        }
        if (StringUtil.isNotEmpty(data)) {
            result.setCode(StaticValues.STR_200).setSuccess(true).setMessage(StaticValues.TRUE_MSG).setData(data);
        }
        return result;
    }

    /*
     * 存量房买卖合同查询接口
     *
     * @param
     * @return
     * @author Ma
     * @since 2024/9/2 16:42
     */
    @RequestMapping("/stockHouseContract")
    public JsonModelResult stockHouseContract(String params){
        JsonModelResult result = JsonModelResult.getInstance();
        result.setCode(StaticValues.STR_500).setSuccess(false).setMessage(StaticValues.FALSE_MSG);
        String data = null;
        try {
            data = htcxService.stockHouseContract(params);
        } catch (OperationException e) {
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("存量房买卖合同查询失败：" + e);
        }
        if (StringUtil.isNotEmpty(data)) {
            result.setCode(StaticValues.STR_200).setSuccess(true).setMessage(StaticValues.TRUE_MSG).setData(data);
        }
        return result;
    }

    /*
     * 租赁合同查询接口查询
     *
     * @param
     * @return
     * @author Ma
     * @since 2024/9/2 16:42
     */
    @RequestMapping("/leaseContract")
    public JsonModelResult leaseContract(String params){
        JsonModelResult result = JsonModelResult.getInstance();
        result.setCode(StaticValues.STR_500).setSuccess(false).setMessage(StaticValues.FALSE_MSG);
        String data = null;
        try {
            data = htcxService.leaseContract(params);
        } catch (OperationException e) {
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("租赁合同查询接口查询失败：" + e);
        }
        if (StringUtil.isNotEmpty(data)) {
            result.setCode(StaticValues.STR_200).setSuccess(true).setMessage(StaticValues.TRUE_MSG).setData(data);
        }
        return result;
    }
}
