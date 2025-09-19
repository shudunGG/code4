package com.hanweb.yyjr.zhangye.service;

/**
 * @className: stcxService
 * @description: TODO 类描述
 * @author: freedom
 * @date: 2024/09/02 16:40
 * @Company: Copyright© 2024 DaHan Inc.
 **/
public interface HtcxService {

    // 商品房买卖合同查询接口
    String newHouseContract(String params);

    // 存量房买卖合同查询接口
    String stockHouseContract(String params);

    // 租赁合同查询接口查询
    String leaseContract(String params);
}
