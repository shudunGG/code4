package com.hanweb.yyjr.zhangye.controller;

import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.JsonModelResult;
import com.hanweb.common.util.security.SecurityUtil;
import com.hanweb.gsszf.constant.RenSheConstant;
import com.hanweb.yyjr.zhangye.service.ZyStaticDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: yyjrzzxt
 * @ClassName: ZyStaticDataController
 * @description: 张掖三十个静态数据（共享交换）接口
 * @author: jq
 * @create: 2024-11-21 22:16
 */
@RestController
@RequestMapping("interface/zhangye/zyStaticData")
public class ZyStaticDataController {

    @Autowired
    private ZyStaticDataService zyStaticDataService;

    @Value("${project.zyStaticData.inPublicKey:0496c0c76be675fe16a2febca4dd7b68bd1cb1d0f48c1302925fd774c9e426e9b6b17ef4e6d0142e803d4046a34b9fc164cf5f418c3700b33d0a8e5d845f53371b}")
    private String inPublicKey;

    @Value("${project.zyStaticData.inPrivateKey:5f715894361bf267af67d8dc8d5749b7039856bc1f2e774cbb4b15847c8c4630}")
    private String inPrivateKey;

    @Value("${project.zyStaticData.outPublicKey:0424cdbdee72be40872b75dd682982c01d7349beb2978f650b5613754900d3399521e98732eec0724d3081a659aef4e38fa683f85e9be1f5f2956b62c03a25d6d2}")
    private String outPublicKey;

    @Value("${project.zyStaticData.outPrivateKey:3019d00052f162b9cb458ec6d38a89310f47e4930b8d6b4659c9d934509a4a2b}")
    private String outPrivateKey;

    /**
     * 获取静态数据
     *
     * @param params 入参（加密）
     * @return
     */
    @PostMapping("getStaticData")
    public JsonModelResult<Object> getStaticData(String params) {
        JsonModelResult<Object> result = JsonModelResult.getInstance();

        if (StringUtil.isEmpty(params)) {
            return result.setCode(RenSheConstant.REQ_CODE_401).setMessage(RenSheConstant.REQ_MSG_401).setSuccess(true);
        }
        params = SecurityUtil.sm2Decode(params, inPrivateKey);
        if (StringUtil.isEmpty(params)) {
            return result.setCode(RenSheConstant.REQ_CODE_402).setMessage(RenSheConstant.REQ_MSG_402).setSuccess(true);
        }

        String req = null;
        try {
            req = zyStaticDataService.getZyStaticData(params);
        } catch (Exception e) {
            return result.setCode("500").setMessage("三方接口调用失败" + e.getMessage()).setSuccess(true);
        }

        req = SecurityUtil.sm2Encode(req, outPublicKey);
        return result.setData(req).setCode("200").setMessage("三方接口调用成功").setSuccess(true);
    }

}
