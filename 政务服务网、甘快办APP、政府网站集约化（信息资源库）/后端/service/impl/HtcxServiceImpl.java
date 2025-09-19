package com.hanweb.yyjr.zhangye.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.hanweb.common.util.HttpSender;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.log.Log;
import com.hanweb.common.util.log.LogFactory;
import com.hanweb.common.util.log.LogWriter;
import com.hanweb.common.util.security.SecurityUtil;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.gsszf.constant.StaticValues;
import com.hanweb.yyjr.zhangye.service.HtcxService;
import com.hanweb.yyjr.zhangye.util.RSAHelper;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @className: StcxServiceImpl
 * @description: TODO 类描述
 * @author: freedom
 * @date: 2024/09/02 16:42
 * @Company: Copyright© 2024 DaHan Inc.
 **/
@Service
public class HtcxServiceImpl implements HtcxService {

    private static final Log LOGGER = LogFactory.getInstance(HtcxServiceImpl.class);

    @Value("${project.htcx.publicKey:04d1f23804bf613f312918ff60a8e4aa776fb27d5215a5e0a5a91c8beb83e3730bf57959821ec439bb54e0cad9cbac2832150cd57344327ba08dd46c616618867b}")
    private String publicKey;

    @Value("${project.htcx.privateKey:00c437670b40c7c9567748b1ae96b0b0ef251cf0fff865ffa27e629e64fb2beffa}")
    private String privateKey;

    //接口公钥（网签交易系统分配）
    String rsaPublicKey = "MIIBojANBgkqhkiG9w0BAQEFAAOCAY8AMIIBigKCAYEAwmSxvQABuG/bY91Nu98ZMj5oeNpEvVBHsfJeHvaiui3yp4rTFpDbcrrMNxNCzdZU9sBOH37I8xlQXF4GGj/R5z05Eu1sntIxtOWu8D5mlriZu1hUsgW7GxgbgdZvMmTOkIPq7NhDT2AweY15mL5WQldnN47aD7ofqgl0KGzwRvPiPc30LqSQzqPMu9GMJt3oQmen4GeEHDmDgP54xOEhOvEIK/PbnEyjxu18t72qHALi7wJW6AYfxD8E27X3RRh9sdu7Tf5sigL7ygQKX10LF+HEESChk17H1DcUmBbH1XiFMeFrHMIajdjmA0kRrgoVZW3u4tceHxAD+0XilAole33k8aJieC+HIYze6KZFO4u4UAXY6qjdQdGeGLLpCK3/y3uwIF2AM+N4pWpLst4rXgDRd3ycvahRJEokl9zEJrR+mJhVPPTWfqtasdFrf3fA2TFJpw7U/SpQrZ8b2k//fwH+ywLwSqK5x9je/a2vBwKHDpUptvfJaE6yBV1CjcJ7AgMBAAE=";


    //用户id，（网签交易系统分配）
    @Value("${project.htcx.sUid:TEST}")
    private String sUid;

    //用户id，（网签交易系统分配）
    @Value("${project.htcx.sourcePath:http://www.zyfcj.com:10005/ibasem/api/public/zhujianErinterface/zhangye/}")
    private String sourcePath;


    @Override
    public String newHouseContract(String params) {
        if (StringUtil.isEmpty(params)) {
            throw new OperationException("入参不能为空！");
        }
        // 入参解密
        params = SecurityUtil.sm2Decode(params, privateKey);
        LOGGER.error("商品房买卖合同查询接口入参：" + params);
        JSONObject requestParams = JSON.parseObject(params);
        String tokenNo = requestParams.getString("tokenNo");
        String authCode = requestParams.getString("authCode");
        if (null == tokenNo && null == authCode) {
            throw new OperationException("获取用户信息失败！");
        }
        requestParams.remove("tokenNo");
        requestParams.remove("authCode");
        LOGGER.error("商品房买卖合同查询接口构建入参：" + requestParams);
        HttpSender httpSender = null;
        String data = null;
        try {
            httpSender = HttpSender.getInstancePost(sourcePath + "zyzjSpfMmhtInfo");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("param", RSAHelper.encryptByPublicKey(rsaPublicKey, JSONObject.toJSONString(requestParams)));
            StringEntity stringEntity = new StringEntity(jsonObject.toJSONString());
            addHttpSender(httpSender);
            httpSender.setRequestBody(stringEntity);
            if (httpSender.execute()) {
                String result = httpSender.getResult();
                if (StringUtil.isNotEmpty(result)) {
                    JSONObject resultJson = JSON.parseObject(result);
                    LOGGER.error("商品房买卖合同查询接口反参：" + resultJson);
                    if (resultJson != null && StringUtil.equals(StaticValues.STR_200, resultJson.getString("code"))) {
                        data = resultJson.getString("data");
                        data = SecurityUtil.sm2Encode(data, publicKey);
                    } else {
                        throw new OperationException(resultJson.getString("message"));
                    }
                } else {
                    throw new OperationException("商品房买卖合同查询接口返回数据为空！");
                }
            } else {
                throw new RuntimeException(getRemoteError(httpSender));
            }
        } catch (OperationException e) {
            throw new OperationException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("商品房买卖合同查询失败，失败信息：" + e);
        }
        return data;
    }

    @Override
    public String stockHouseContract(String params) {
        if (StringUtil.isEmpty(params)) {
            throw new OperationException("入参不能为空！");
        }
        // 入参解密
        params = SecurityUtil.sm2Decode(params, privateKey);
        LOGGER.error("存量房买卖合同查询接口入参：" + params);
        JSONObject requestParams = JSON.parseObject(params);
        String tokenNo = requestParams.getString("tokenNo");
        String authCode = requestParams.getString("authCode");
        if (null == tokenNo && null == authCode) {
            throw new OperationException("获取用户信息失败！");
        }
        requestParams.remove("tokenNo");
        requestParams.remove("authCode");
        LOGGER.error("存量房买卖合同查询接口构建入参：" + requestParams);
        HttpSender httpSender = null;
        String data = null;
        try {
            httpSender = HttpSender.getInstancePost(sourcePath + "zyzjClfMmhtInfo");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("param", RSAHelper.encryptByPublicKey(rsaPublicKey, JSONObject.toJSONString(requestParams)));
            StringEntity stringEntity = new StringEntity(jsonObject.toJSONString());
            addHttpSender(httpSender);
            httpSender.setRequestBody(stringEntity);
            if (httpSender.execute()) {
                String result = httpSender.getResult();
                if (StringUtil.isNotEmpty(result)) {
                    JSONObject resultJson = JSON.parseObject(result);
                    LOGGER.error("存量房买卖合同查询接口接口反参：" + resultJson);
                    if (resultJson != null && StringUtil.equals(StaticValues.STR_200, resultJson.getString("code"))) {
                        data = resultJson.getString("data");
                        data = SecurityUtil.sm2Encode(data, publicKey);
                    } else {
                        throw new OperationException(resultJson.getString("message"));
                    }
                } else {
                    throw new OperationException("存量房买卖合同查询接口返回数据为空！");
                }
            } else {
                throw new RuntimeException(getRemoteError(httpSender));
            }
        } catch (OperationException e) {
            throw new OperationException(e.getMessage());
        } catch (Exception e){
            LOGGER.error("存量房买卖合同查询失败，失败信息：" + e);
        }
        return data;
    }

    @Override
    public String leaseContract(String params) {
        if (StringUtil.isEmpty(params)) {
            throw new OperationException("入参不能为空！");
        }
        // 入参解密
        params = SecurityUtil.sm2Decode(params, privateKey);
        LOGGER.error("租赁合同查询接口入参：" + params);
        JSONObject requestParams = JSON.parseObject(params);
        String tokenNo = requestParams.getString("tokenNo");
        String authCode = requestParams.getString("authCode");
        if (null == tokenNo && null == authCode) {
            throw new OperationException("获取用户信息失败！");
        }
        requestParams.remove("tokenNo");
        requestParams.remove("authCode");
        LOGGER.error("租赁合同查询接口构建入参：" + requestParams);
        HttpSender httpSender = null;
        String data = null;
        try {
            httpSender = HttpSender.getInstancePost(sourcePath + "zyzjZlfMmhtInfo");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("param", RSAHelper.encryptByPublicKey(rsaPublicKey, JSONObject.toJSONString(requestParams)));
            StringEntity stringEntity = new StringEntity(jsonObject.toJSONString());
            addHttpSender(httpSender);
            httpSender.setRequestBody(stringEntity);
            if (httpSender.execute()) {
                String result = httpSender.getResult();
                if (StringUtil.isNotEmpty(result)) {
                    JSONObject resultJson = JSON.parseObject(result);
                    LOGGER.error("租赁合同查询接口反参：" + resultJson);
                    if (resultJson != null && StringUtil.equals(StaticValues.STR_200, resultJson.getString("code"))) {
                        data = resultJson.getString("data");
                        data = SecurityUtil.sm2Encode(data, publicKey);
                    } else {
                        throw new OperationException(resultJson.getString("message"));
                    }
                } else {
                    throw new OperationException("租赁合同查询接口返回数据为空！");
                }
            } else {
                throw new RuntimeException(getRemoteError(httpSender));
            }
        } catch (OperationException e) {
            throw new OperationException(e.getMessage());
        } catch (Exception e){
            LOGGER.error("租赁合同查询失败，失败信息：" + e);
        }
        return data;
    }

    public void addHttpSender(HttpSender httpSender){
        String sTimeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        String sSignature = RSAHelper.encryptByPublicKey(rsaPublicKey, String.format("%s %s", sUid, sTimeStamp));
        httpSender.addHeader("Content-Type", "application/json");
        httpSender.addHeader("south-uid", sUid);
        httpSender.addHeader("south-timestamp", sTimeStamp);
        httpSender.addHeader("south-signature", sSignature);
    }

    /**
     * 获取远程调用失败信息
     *
     * @param sender 发送请求对象
     * @return
     **/
    public static String getRemoteError(HttpSender sender) {
        StringBuilder sb = new StringBuilder("远程调用失败:");
        try (InputStream inputStream = sender.getInputStream();
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            LogWriter.error("发生异常", e);
        }
        return sb.toString();
    }
}
