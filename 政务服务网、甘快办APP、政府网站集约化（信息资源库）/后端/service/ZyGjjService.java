package com.hanweb.yyjr.zhangye.service;

import com.hanweb.common.util.mvc.JsonResult;

import javax.servlet.http.HttpServletResponse;

/**
 * @author zds
 * @className ZyGjjService
 * @description 张掖市公积金service
 * @date: 2022/3/31 9:42
 */
public interface ZyGjjService {

    /**
     * 公积金查询入口
     *
     * @param result    结果集
     * @param bodyParam 请求体json
     * @return
     */
    JsonResult execute(JsonResult result, String bodyParam);

    /**
     * 公积金职工缴存证明和异地贷款缴存证明下载入口
     *
     * @param result    结果集
     * @param bodyParam 请求体json
     * @param response  响应
     * @return
     */
    Object downLoadProve(JsonResult result, String bodyParam, HttpServletResponse response);

}
