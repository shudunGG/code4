package com.hanweb.yyjr.zhangye.util;

import com.alibaba.fastjson.JSONObject;
import com.hanweb.common.util.mvc.JsonModelResult;
import org.springframework.beans.factory.annotation.Value;

/**
 * @program: yyjrzzxt
 * @ClassName: HospitalUtil
 * @description: 医院工具类
 * @author: jq
 * @create: 2024-11-05 11:08
 */
public class HospitalUtil {

    //平台公钥 rsa
    private static final String AREAPKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC9+UTZdH3U0JrvkDARv9/OGPs+vmALlXzYjNGMCCMoWjK7+qSfYtxMl1C8jWV02R7T92nivGtQnEYNRA3V9ftd8HuoeXEk1FckzUwk7YD/+14Sw2puYTLgQpJRqjRnYeX508h8rOGo2iwiVssaoHvBFoXb7lLvWi35NPr+iN2yDQIDAQAB";

    //应用私钥 sm4
    private static final String DATASECKEY = "f8cbb2e831f28e03";
//    public static String getHospitalParams(String json, String userCode) {
//        JSONObject paramsJSON = new JSONObject();
//
//
//    }
}
