package com.hanweb.yyjr.zhangye.controller;

import com.hanweb.common.util.log.LogWriter;
import com.hanweb.common.util.mvc.JsonModelResult;
import com.hanweb.gsszf.constant.StaticValues;
import com.hanweb.gsszf.dto.zy.MinCountyParamDto;
import com.hanweb.yyjr.zhangye.service.ZyHospitalService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: yyjrzzxt
 * @ClassName: HospitalController
 * @description: 张掖医院
 * @author: jq
 * @create: 2024-11-04 21:38
 */

@RestController
@RequestMapping("interface/zhangye/hospital")
public class ZyTjHospitalController {

    @Autowired
    private ZyHospitalService hospitalService;

    @PostMapping("checkList")
    public JsonModelResult<String> checkList(@RequestBody MinCountyParamDto minCountyParamDto) {
        JsonModelResult<String> docInfo = JsonModelResult.getInstance();
        boolean success = false;
        String message;
        String result = "";
        String code = StaticValues.STR_500;
        if (null == minCountyParamDto || StringUtils.isEmpty(minCountyParamDto.getResults())) {
            docInfo.setCode(StaticValues.STR_500).setSuccess(false).setMessage("入参不能为空！");
            return docInfo;
        }
        try {
            result = hospitalService.checkList(minCountyParamDto);
            success = true;
            code = StaticValues.STR_200;
            message = StaticValues.SUCCESS_MESSAGE;
        } catch (Exception e) {
            LogWriter.info("检报告列表查询接口异常：" + e);
            message = e.getMessage();
        }
        return docInfo.setData(result).setCode(code).setSuccess(success).setMessage(message);
    }

    @PostMapping("checkInfo")
    public JsonModelResult<String> checkInfo(@RequestBody MinCountyParamDto minCountyParamDto) {
        JsonModelResult<String> docInfo = JsonModelResult.getInstance();
        boolean success = false;
        String message;
        String result = "";
        String code = StaticValues.STR_500;
        if (null == minCountyParamDto || StringUtils.isEmpty(minCountyParamDto.getResults())) {
            docInfo.setCode(StaticValues.STR_500).setSuccess(false).setMessage("入参不能为空！");
            return docInfo;
        }
        try {
            result = hospitalService.checkInfo(minCountyParamDto);
            success = true;
            code = StaticValues.STR_200;
            message = StaticValues.SUCCESS_MESSAGE;
        } catch (Exception e) {
            LogWriter.info("检报告列表查询接口异常：" + e);
            message = e.getMessage();
        }
        return docInfo.setData(result).setCode(code).setSuccess(success).setMessage(message);
    }

    @PostMapping("checkPdfInfo")
    public JsonModelResult<String> checkPdfInfo(@RequestBody MinCountyParamDto minCountyParamDto) {
        JsonModelResult<String> docInfo = JsonModelResult.getInstance();
        boolean success = false;
        String message;
        String result = "";
        String code = StaticValues.STR_500;
        if (null == minCountyParamDto || StringUtils.isEmpty(minCountyParamDto.getResults())) {
            docInfo.setCode(StaticValues.STR_500).setSuccess(false).setMessage("入参不能为空！");
            return docInfo;
        }
        try {
            result = hospitalService.checkPdfInfo(minCountyParamDto);
            success = true;
            code = StaticValues.STR_200;
            message = StaticValues.SUCCESS_MESSAGE;
        } catch (Exception e) {
            LogWriter.info("检报告列表查询接口异常：" + e);
            message = e.getMessage();
        }
        return docInfo.setData(result).setCode(code).setSuccess(success).setMessage(message);
    }

    /**
     * 肃南体检报告
     * @param tokenNo
     * @param authCode
     * @return
     */
    @PostMapping("snPhysical")
    public JsonModelResult<String> snPhysical(String tokenNo, String authCode) {
        JsonModelResult<String> docInfo = JsonModelResult.getInstance();
        boolean success = false;
        String message;
        String result = "";
        String code = StaticValues.STR_500;
        if (StringUtils.isEmpty(tokenNo)) {
            docInfo.setCode(StaticValues.STR_500).setSuccess(false).setMessage("入参不能为空！");
            return docInfo;
        }
        try {
            result = hospitalService.snPhysical(tokenNo, authCode);
            success = true;
            code = StaticValues.STR_200;
            message = StaticValues.SUCCESS_MESSAGE;
        } catch (Exception e) {
            LogWriter.info("检报告列表查询接口异常：" + e.getMessage());
            message = e.getMessage();
        }
        return docInfo.setData(result).setCode(code).setSuccess(success).setMessage(message);
    }

    /**
     * 甘州体检报告
     *
     * @param tokenNo
     * @param authCode
     * @param current  页码
     * @param size     页大小
     * @param orderBy  排序
     * @return
     */
    @PostMapping("gzPhysical")
    public JsonModelResult<String> gzPhysical(String tokenNo, String authCode, String current, String size, String orderBy) {
        JsonModelResult<String> docInfo = JsonModelResult.getInstance();
        boolean success = false;
        String message;
        String result = "";
        String code = StaticValues.STR_500;
        if (StringUtils.isEmpty(tokenNo)) {
            docInfo.setCode(StaticValues.STR_500).setSuccess(false).setMessage("入参不能为空！");
            return docInfo;
        }
        try {
            result = hospitalService.gzPhysical(tokenNo, authCode, current, size, orderBy);
            success = true;
            code = StaticValues.STR_200;
            message = StaticValues.SUCCESS_MESSAGE;
        } catch (Exception e) {
            LogWriter.info("检报告列表查询接口异常：" + e.getMessage());
            message = e.getMessage();
        }
        return docInfo.setData(result).setCode(code).setSuccess(success).setMessage(message);
    }

}
