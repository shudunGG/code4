package com.hanweb.yyjr.zhangye.service;

import com.hanweb.gsszf.dto.zy.MinCountyParamDto;

public interface ZyHospitalService {

    /**
     * 体检报告列表查询
     *
     * @param minCountyParamDto sm2加密入参
     * @return sm2加密体检报告列表查询接口
     */
    String checkList(MinCountyParamDto minCountyParamDto);

    /**
     * 体检报告详情查询
     *
     * @param minCountyParamDto sm2加密入参
     * @return sm2加密体检报告详情
     */
    String checkInfo(MinCountyParamDto minCountyParamDto);

    /**
     * 体检报告pdf查询
     *
     * @param minCountyParamDto sm2加密入参
     * @return sm2加密体检报告详情
     */
    String checkPdfInfo(MinCountyParamDto minCountyParamDto);


    String snPhysical(String tokenNo, String authCode);

    /**
     * 甘州体检
     * @param tokenNo
     * @param authCode
     * @param current 页码
     * @param size 页大小
     * @param orderBy
     * @return
     */
    String gzPhysical(String tokenNo, String authCode, String current, String size, String orderBy);
}
