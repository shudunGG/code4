package com.hanweb.yyjr.zhangye.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hanweb.common.cache.Cache;
import com.hanweb.common.cache.CacheManager;
import com.hanweb.common.util.HttpSender;
import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.json.Type;
import com.hanweb.common.util.log.Log;
import com.hanweb.common.util.log.LogFactory;
import com.hanweb.common.util.security.SecurityUtil;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.gsszf.config.StaticValues2;
import com.hanweb.gsszf.dto.zy.MinCountyParamDto;
import com.hanweb.gsszf.dto.zy.ZyHospitalConfiguration;
import com.hanweb.gsszf.member.IndividualsAndCorporation;
import com.hanweb.yyjr.zhangye.service.ZyHospitalService;
import com.hanweb.yyjr.zhangye.util.HttpUtil;
import com.hanweb.yyjr.zhangye.util.IdentityUtil;
import com.hanweb.yyjr.zhangye.util.Rsa2SignUtil;
import com.hanweb.yyjr.zhangye.util.RsaUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sviolet.smcrypto.Sm2Key;

import javax.annotation.Resource;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;

import static com.hanweb.yyjr.zhangye.service.impl.HtcxServiceImpl.getRemoteError;

/**
 * @program: yyjrzzxt
 * @ClassName: HospitalServiceImpl
 * @description: 医院服务
 * @author: jq
 * @create: 2024-11-05 09:31
 */
@Service
public class ZyHospitalServiceImpl implements ZyHospitalService {


    private static final Log LOGGER = LogFactory.getInstance(ZyHospitalService.class);


    /**
     * 甘州token 缓存
     */
    private static final Cache GZ_TOKEN = CacheManager.getInstance(StaticValues2.GZ_TOKEN);

    private static final Cache SU_BEI_HEALTH_CARE_CACHE = CacheManager.getInstance(StaticValues2.SU_BEI_HEALTH_CARE_CACHE);

    @Resource
    private IdentityUtil identityUtil;
    @Autowired
    private ZyHospitalConfiguration zyHospitalConfiguration;

    /**
     * 体检报告列表查询
     *
     * @param minCountyParamDto sm2加密入参
     * @return sm2加密体检报告列表查询接口
     */
    @Override
    public String checkList(MinCountyParamDto minCountyParamDto) {
        HttpClient client;
        HttpRequest httpRequest;
        String result;
        String request;
        String url;
        JSONObject json = JSON.parseObject(SecurityUtil.sm2Decode(minCountyParamDto.getResults(),
                zyHospitalConfiguration.getInPrivKey()));
        if (StringUtil.isEmpty(json.getString("timeType")) ||
                StringUtil.isEmpty(json.getString("reportType"))) {
            throw new OperationException("必传参数不能为空！");
        }
        String userCode;
        //应用公钥
        String appPriKey;
        String requestUrl;
        //16位数据秘钥
        String cloudDataKey;
        String dataType = json.getString("dataType");
        if (StringUtils.equals(StaticValues2.LZXZYY, dataType)) {
            userCode = zyHospitalConfiguration.getLzxUserName();
            appPriKey = zyHospitalConfiguration.getLzxBaseIkey();
            requestUrl = zyHospitalConfiguration.getLzxIp()
                    + zyHospitalConfiguration.getLzxCheckList();
            cloudDataKey = zyHospitalConfiguration.getLzxDataSecKey();
        } else if (StringUtils.equals(StaticValues2.MLYY, dataType)) {
            userCode = zyHospitalConfiguration.getMlUserName();
            appPriKey = zyHospitalConfiguration.getMlBaseIkey();
            requestUrl = zyHospitalConfiguration.getMlIp()
                    + zyHospitalConfiguration.getLzxCheckList();
            cloudDataKey = zyHospitalConfiguration.getMlDataSecKey();
        } else if (StringUtils.equals(StaticValues2.GTYY, dataType)) {
            userCode = zyHospitalConfiguration.getGtUserName();
            appPriKey = zyHospitalConfiguration.getGtBaseIkey();
            requestUrl = zyHospitalConfiguration.getGtIp()
                    + zyHospitalConfiguration.getLzxCheckList();
            cloudDataKey = zyHospitalConfiguration.getGtDataSecKey();
        } else if (StringUtils.equals(StaticValues2.GTZYY, dataType)) {
            userCode = zyHospitalConfiguration.getGtzyyUserName();
            appPriKey = zyHospitalConfiguration.getGtzyyBaseIkey();
            requestUrl = zyHospitalConfiguration.getGtzyyIp()
                    + zyHospitalConfiguration.getLzxCheckList();
            cloudDataKey = zyHospitalConfiguration.getGtzyyDataSecKey();
        } else {
            throw new OperationException("暂无类型！");
        }


        String token = json.getString("token");

        String minToken = getToken(dataType);
        IndividualsAndCorporation userInfo
                = identityUtil.getUserInfo(json.getString("token"), json.getString("authCode"));

        userInfo.setUserName(json.getString("patientName"));
        userInfo.setCardNo(json.getString("cardNo"));
        userInfo.setMobile(json.getString("phone"));
        JSONObject requestObject = new JSONObject();

        if ("TOKEN_LXZYY_CS".equals(token)) {
            requestObject.put("patientName", "张国晖");
            requestObject.put("cardNo", "620102198503036217");
            requestObject.put("phone", "13830621045");
        } else if ("TOKEN_MLYY_CS".equals(token)) {
            requestObject.put("patientName", "周雪琴");
            requestObject.put("cardNo", "622223198803124621");
            requestObject.put("phone", "18193678155");
        } else if ("TOKEN_GTYY_CS".equals(token)) {
            requestObject.put("patientName", "周雪琴");
            requestObject.put("cardNo", "622223198803124621");
            requestObject.put("phone", "18193678155");
        } else {
            requestObject.put("patientName", userInfo.getUserName());
            requestObject.put("cardNo", userInfo.getCardNo());
            requestObject.put("phone", userInfo.getMobile());
        }

        requestObject.put("timeType", json.getString("timeType"));
        requestObject.put("startTime", json.getString("startTime"));
        requestObject.put("endTime", json.getString("endTime"));
        requestObject.put("reportType", json.getString("reportType"));
        requestObject.put(StaticValues2.TIMESTAMP, String.valueOf(System.currentTimeMillis() / 1000));
        request = Rsa2SignUtil.sm4Encode(requestObject.toJSONString(), cloudDataKey);
        String appSign;
        try {
            appSign = RsaUtil.sign(requestObject.toJSONString().getBytes(StandardCharsets.UTF_8)
                    , Base64.getDecoder().decode(appPriKey)
                    , StaticValues2.SHA256WITHRSA);
        } catch (Exception e) {
            LOGGER.error("验签方法异常！", e);
            throw new OperationException(e.getMessage());
        }
        if (StringUtil.isEmpty(appSign)) {
            throw new OperationException("签名失败！");
        }
        String sign = URLEncoder.encode(appSign, StandardCharsets.UTF_8);
        url = requestUrl + "?sign=" + sign + "&request=" + request
                + "&userCode=" + userCode;
        // 生成HttpSender请求对象
        LOGGER.info(dataType + "健康医疗体检报告列表接口 " + url);
        client = HttpClient.newBuilder()
                // 设置连接超时时长为30秒
                .connectTimeout(Duration.ofSeconds(20))
                .build();
        httpRequest = HttpRequest
                .newBuilder(URI.create(url))
                .header("Content-Type", "application/json;charset=utf-8")
                .header("Authorization", "Bearer " + minToken)
                .GET()
                .timeout(Duration.ofSeconds(20))
                .build();
        try {
            final HttpResponse.BodyHandler<String> inputStreamBodyHandler = HttpResponse.BodyHandlers.ofString();
            CompletableFuture<HttpResponse<String>> httpResponseCompletableFuture = client.sendAsync(httpRequest, inputStreamBodyHandler);
            HttpResponse<String> stringHttpResponse = httpResponseCompletableFuture.get();
            result = stringHttpResponse.body();
            LOGGER.info(dataType + "健康医疗 - " + url + "接口请求三方反参：" + result);
            //sm4解密
            JSONObject response = JSON.parseObject(result);
            Boolean ret = response.getBoolean("ret");
            if (!ret) {
                LOGGER.error("返回状态为失败！" + JsonUtil.objectToString(result));
                throw new OperationException(response.getString("msg"));
            }
            result = Rsa2SignUtil.sm4Decode(response.getString("data"), cloudDataKey);
            LOGGER.debug("返回数据解密后：" + result);
            result = JSON.parseArray(result).toJSONString();
            result = SecurityUtil.sm2Encode(result, zyHospitalConfiguration.getOutPublicKey());
        } catch (Exception e) {
            LOGGER.error(dataType + "健康医疗体检报告列表查询出现异常：", e);
            throw new OperationException(dataType + "健康医疗体检报告列表查询请求出现异常：" + e.getMessage());
        }
        return result;
    }

    /**
     * 体检报告详情查询
     *
     * @param minCountyParamDto sm2加密入参
     * @return sm2加密体检报告详情
     */
    @Override
    public String checkInfo(MinCountyParamDto minCountyParamDto) {
        HttpClient client;
        HttpRequest httpRequest;
        String result;
        String request;
        String url;
        JSONObject json = JSON.parseObject(SecurityUtil.sm2Decode(minCountyParamDto.getResults(),
                zyHospitalConfiguration.getInPrivKey()));
        if (StringUtil.isEmpty(json.getString("id"))) {
            throw new OperationException("必传参数不能为空！");
        }
        String userCode;
        String appPriKey;
        String requestUrl;
        String cloudDataKey;
        String dataType = json.getString("dataType");
        if (StringUtils.equals(StaticValues2.LZXZYY, dataType)) {
            userCode = zyHospitalConfiguration.getLzxUserName();
            appPriKey = zyHospitalConfiguration.getLzxBaseIkey();
            requestUrl = zyHospitalConfiguration.getLzxIp()
                    + zyHospitalConfiguration.getLzxCheckInfo();
            cloudDataKey = zyHospitalConfiguration.getLzxDataSecKey();
        } else if (StringUtils.equals(StaticValues2.MLYY, dataType)) {
            userCode = zyHospitalConfiguration.getMlUserName();
            appPriKey = zyHospitalConfiguration.getMlBaseIkey();
            requestUrl = zyHospitalConfiguration.getMlIp()
                    + zyHospitalConfiguration.getLzxCheckInfo();
            cloudDataKey = zyHospitalConfiguration.getMlDataSecKey();
        } else if (StringUtils.equals(StaticValues2.GTYY, dataType)) {
            userCode = zyHospitalConfiguration.getGtUserName();
            appPriKey = zyHospitalConfiguration.getGtBaseIkey();
            requestUrl = zyHospitalConfiguration.getGtIp()
                    + zyHospitalConfiguration.getLzxCheckInfo();
            cloudDataKey = zyHospitalConfiguration.getGtDataSecKey();
        } else if (StringUtils.equals(StaticValues2.GTZYY, dataType)) {
            userCode = zyHospitalConfiguration.getGtzyyUserName();
            appPriKey = zyHospitalConfiguration.getGtzyyBaseIkey();
            requestUrl = zyHospitalConfiguration.getGtzyyIp()
                    + zyHospitalConfiguration.getLzxCheckInfo();
            cloudDataKey = zyHospitalConfiguration.getGtzyyDataSecKey();
        } else {
            throw new OperationException("暂无类型！");
        }
        String minToken = getToken(dataType);
        JSONObject requestObject = new JSONObject();
        requestObject.put("id", json.getString("id"));
        requestObject.put(StaticValues2.TIMESTAMP, String.valueOf(System.currentTimeMillis() / 1000));
        request = Rsa2SignUtil.sm4Encode(requestObject.toJSONString(), cloudDataKey);
        String appSign;
        try {
            appSign = RsaUtil.sign(requestObject.toJSONString().getBytes(StandardCharsets.UTF_8)
                    , Base64.getDecoder().decode(appPriKey)
                    , StaticValues2.SHA256WITHRSA);
        } catch (Exception e) {
            LOGGER.error("验签方法异常！", e);
            throw new OperationException(e.getMessage());
        }
        if (StringUtil.isEmpty(appSign)) {
            throw new OperationException("签名失败！");
        }
        String sign = URLEncoder.encode(appSign, StandardCharsets.UTF_8);
        url = requestUrl + "?sign=" + sign + "&request=" + request
                + "&userCode=" + userCode;
        // 生成HttpSender请求对象
        LOGGER.info(dataType + "健康医疗体检报告详情查询接口 " + url);
        client = HttpClient.newBuilder()
                // 设置连接超时时长为30秒
                .connectTimeout(Duration.ofSeconds(20))
                .build();
        httpRequest = HttpRequest
                .newBuilder(URI.create(url))
                .header("Content-Type", "application/json;charset=utf-8")
                .header("Authorization", "Bearer " + minToken)
                .GET()
                .timeout(Duration.ofSeconds(20))
                .build();
        try {
            final HttpResponse.BodyHandler<String> inputStreamBodyHandler = HttpResponse.BodyHandlers.ofString();
            CompletableFuture<HttpResponse<String>> httpResponseCompletableFuture = client.sendAsync(httpRequest, inputStreamBodyHandler);
            HttpResponse<String> stringHttpResponse = httpResponseCompletableFuture.get();
            result = stringHttpResponse.body();
            LOGGER.info(dataType + "健康医疗 - " + url + "接口请求三方反参：" + result);
            //sm4解密
            JSONObject response = JSON.parseObject(result);
            Boolean ret = response.getBoolean("ret");
            if (!ret) {
                LOGGER.error("返回状态为失败！" + JsonUtil.objectToString(result));
                throw new OperationException(response.getString("msg"));
            }
            result = Rsa2SignUtil.sm4Decode(response.getString("data"), cloudDataKey);
            result = JSON.parseArray(result).toJSONString();
            result = SecurityUtil.sm2Encode(result, zyHospitalConfiguration.getOutPublicKey());
        } catch (Exception e) {
            LOGGER.error(dataType + "健康医疗体检报告详情查询出现异常：", e);
            throw new OperationException(dataType + "健康医疗体检报告详情查询请求出现异常：" + e.getMessage());
        }
        return result;
    }

    @Override
    public String checkPdfInfo(MinCountyParamDto minCountyParamDto) {
        HttpClient client;
        HttpRequest httpRequest;
        String result;
        String request;
        String url;
        JSONObject json = JSON.parseObject(SecurityUtil.sm2Decode(minCountyParamDto.getResults(),
                zyHospitalConfiguration.getInPrivKey()));
        if (StringUtil.isEmpty(json.getString("id"))) {
            throw new OperationException("必传参数不能为空！");
        }
        String userCode;
        String appPriKey;
        String requestUrl;
        String cloudDataKey;
        String dataType = json.getString("dataType");
        if (StringUtils.equals(StaticValues2.LZXZYY, dataType)) {
            userCode = zyHospitalConfiguration.getLzxUserName();
            appPriKey = zyHospitalConfiguration.getLzxBaseIkey();
            requestUrl = zyHospitalConfiguration.getLzxIp()
                    + zyHospitalConfiguration.getLzxexamPdf();
            cloudDataKey = zyHospitalConfiguration.getLzxDataSecKey();
        } else if (StringUtils.equals(StaticValues2.MLYY, dataType)) {
            userCode = zyHospitalConfiguration.getMlUserName();
            appPriKey = zyHospitalConfiguration.getMlBaseIkey();
            requestUrl = zyHospitalConfiguration.getMlIp()
                    + zyHospitalConfiguration.getLzxexamPdf();
            cloudDataKey = zyHospitalConfiguration.getMlDataSecKey();
        } else if (StringUtils.equals(StaticValues2.GTYY, dataType)) {
            userCode = zyHospitalConfiguration.getGtUserName();
            appPriKey = zyHospitalConfiguration.getGtBaseIkey();
            requestUrl = zyHospitalConfiguration.getGtIp()
                    + zyHospitalConfiguration.getLzxexamPdf();
            cloudDataKey = zyHospitalConfiguration.getGtDataSecKey();
        } else if (StringUtils.equals(StaticValues2.GTZYY, dataType)) {
            userCode = zyHospitalConfiguration.getGtzyyUserName();
            appPriKey = zyHospitalConfiguration.getGtzyyBaseIkey();
            requestUrl = zyHospitalConfiguration.getGtzyyIp()
                    + zyHospitalConfiguration.getLzxexamPdf();
            cloudDataKey = zyHospitalConfiguration.getGtzyyDataSecKey();
        } else {
            throw new OperationException("暂无类型！");
        }
        String minToken = getToken(dataType);
        JSONObject requestObject = new JSONObject();
        requestObject.put("id", json.getString("id"));
        requestObject.put(StaticValues2.TIMESTAMP, String.valueOf(System.currentTimeMillis() / 1000));
        request = Rsa2SignUtil.sm4Encode(requestObject.toJSONString(), cloudDataKey);
        String appSign;
        try {
            appSign = RsaUtil.sign(requestObject.toJSONString().getBytes(StandardCharsets.UTF_8)
                    , Base64.getDecoder().decode(appPriKey)
                    , StaticValues2.SHA256WITHRSA);
        } catch (Exception e) {
            LOGGER.error("验签方法异常！", e);
            throw new OperationException(e.getMessage());
        }
        if (StringUtil.isEmpty(appSign)) {
            throw new OperationException("签名失败！");
        }
        String sign = URLEncoder.encode(appSign, StandardCharsets.UTF_8);
        url = requestUrl + "?sign=" + sign + "&request=" + request
                + "&userCode=" + userCode;
        // 生成HttpSender请求对象
        LOGGER.info(dataType + "健康医疗体检报告详情查询接口 " + url);
        client = HttpClient.newBuilder()
                // 设置连接超时时长为30秒
                .connectTimeout(Duration.ofSeconds(20))
                .build();
        httpRequest = HttpRequest
                .newBuilder(URI.create(url))
                .header("Content-Type", "application/json;charset=utf-8")
                .header("Authorization", "Bearer " + minToken)
                .GET()
                .timeout(Duration.ofSeconds(20))
                .build();
        try {
            final HttpResponse.BodyHandler<String> inputStreamBodyHandler = HttpResponse.BodyHandlers.ofString();
            CompletableFuture<HttpResponse<String>> httpResponseCompletableFuture = client.sendAsync(httpRequest, inputStreamBodyHandler);
            HttpResponse<String> stringHttpResponse = httpResponseCompletableFuture.get();
            result = stringHttpResponse.body();
            LOGGER.info(dataType + "健康医疗 - " + url + "接口请求三方反参：" + result);
            //sm4解密
            JSONObject response = JSON.parseObject(result);
            Boolean ret = response.getBoolean("ret");
            String code = response.getString("code");
            if (!ret || !"1".equals(code)) {
                LOGGER.error("返回状态为失败！" + JsonUtil.objectToString(result));
                throw new OperationException(response.getString("msg"));
            }
            result = Rsa2SignUtil.sm4Decode(response.getString("data"), cloudDataKey);
            LOGGER.debug("返回数据解密后：" + result);
            result = SecurityUtil.sm2Encode(result, zyHospitalConfiguration.getOutPublicKey());
        } catch (Exception e) {
            LOGGER.error(dataType + "健康医疗体检报告详情查询出现异常：", e);
            throw new OperationException(dataType + "健康医疗体检报告详情查询请求出现异常：" + e.getMessage());
        }
        return result;
    }

    @Override
    public String snPhysical(String tokenNo, String authCode) {
        tokenNo = SecurityUtil.sm2Decode(tokenNo, zyHospitalConfiguration.getInPrivKey());
        authCode = SecurityUtil.sm2Decode(authCode, zyHospitalConfiguration.getInPrivKey());

        if (StringUtil.isEmpty(tokenNo)) {
            throw new OperationException("tokenNo解密失败！");
        }
        IndividualsAndCorporation userInfo
                = identityUtil.getUserInfo(tokenNo, authCode);
        String idCardStr = userInfo.getCardNo();

        JSONObject body = new JSONObject();

        body.put("idCard", idCardStr);
//        body.put("idCard", "41122319690428705X");
        body.put("sign", "UKy3NLEm7iSPq1Anu5J6TjfYdVlFDWh0");

        try (HttpSender httpSender = HttpSender.getInstancePost(zyHospitalConfiguration.getSnTjUrl())) {
            StringEntity postingString = new StringEntity(JsonUtil.objectToString(body), "UTF-8");
            httpSender.setRequestBody(postingString);
            httpSender.addHeader("Content-Type", "application/json");

            if (httpSender.execute()) {
                String req = httpSender.getResult();
                LOGGER.debug("肃南体检报告查询接口返回：" + req);
                req = SecurityUtil.sm2Encode(req, zyHospitalConfiguration.getOutPublicKey());
                return req;
            } else {
                throw new RuntimeException("肃南体检报告查询接口请求失败！<CODE>" + httpSender.getCode() + "-->" + getRemoteError(httpSender));
            }
        } catch (Exception e) {
            LOGGER.error("肃南体检报告查询接口请求异常！", e);
            throw new RuntimeException("肃南体检报告查询接口请求异常！");
        }

    }

    @Override
    public String gzPhysical(String tokenNo, String authCode, String current, String size, String orderBy) {

        tokenNo = SecurityUtil.sm2Decode(tokenNo, zyHospitalConfiguration.getInPrivKey());
        authCode = SecurityUtil.sm2Decode(authCode, zyHospitalConfiguration.getInPrivKey());

        if (StringUtil.isEmpty(tokenNo)) {
            throw new OperationException("tokenNo解密失败！");
        }
        IndividualsAndCorporation userInfo
                = identityUtil.getUserInfo(tokenNo, authCode);
        String idCardStr = userInfo.getCardNo();
        idCardStr = "622201198905276919";

        StringBuilder sb = new StringBuilder(zyHospitalConfiguration.getGzTjUrl());
        sb.append("?current=").append(current).append("&size=").append(size).append("&orderBy=").append(orderBy)
                .append("&IDCARDNO=" + idCardStr);

        String gzToken = null;
        try {
            gzToken = getGzToken();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        try (HttpSender httpSender = HttpSender.getInstanceGet(sb.toString())) {
            httpSender.addHeader("e-app-token", gzToken);
            httpSender.addHeader("client_key", zyHospitalConfiguration.getClientKey());

            if (httpSender.execute()) {
                String req = httpSender.getResult();
                LOGGER.debug("甘州体检报告查询接口返回：" + req);
                req = SecurityUtil.sm2Encode(req, zyHospitalConfiguration.getOutPublicKey());
                return req;
            } else {
                throw new RuntimeException("甘州体检报告查询接口请求失败！<CODE>" + httpSender.getCode() + "-->" + getRemoteError(httpSender));
            }

        } catch (Exception e) {
            LOGGER.error("甘州体检报告查询接口请求异常！", e);
            throw new RuntimeException("甘州体检报告查询接口请求异常！");
        }
    }

    public String getGzToken() {
        String token = GZ_TOKEN.get("ganzhou_tijian_token", new Type<String>() {
            private static final long serialVersionUID = -3526910032788607932L;
        });

        if (StringUtil.isNotEmpty(token)) {
            return token;
        }
        JSONObject body = new JSONObject();
        body.put("account", "test");
        body.put("password", "test_123");
        body.put("token", "eyJhbGciOiJIUzUxMiIsInppcCI6IkRFRiJ9.eNocy1sOgjAQheG9zKOhyUxnphcWQwK0NWgChoLRGPcu-Pr953zgtk3QgkcphUkMZWEjvSMzII-mjD5xkFBSQmig7sMxJsQopE7FSxDFQK67dHT0qdajb8s9z6bm9ZnXE_sNWvJMkWwkaSC_Hn-wGNSf0M_vtFzPa2SVoDZiVBVk6wS-PwAAAP__.gGNrg2V6wjEBEefZGtV49Q6eqPDCT5yqSSyaHNQ09OM25QRkC5N0zw4hb0fQwg0tXuE1QCZxqYqTl9Pajmw9vw");
        body.put("refreshPeriodTime", 600000);

        try (HttpSender httpSender = HttpSender.getInstancePost(zyHospitalConfiguration.getGzTokenUrl())) {
            httpSender.addHeader("client_key", "1698303299");

            StringEntity postingString = new StringEntity(JsonUtil.objectToString(body), "UTF-8");
            httpSender.setRequestBody(postingString);

            if (httpSender.execute()) {
                String req = httpSender.getResult();
                LOGGER.debug("甘州体检报告查询获取token接口返回：" + req);
                JSONObject jsonObject = JSON.parseObject(req);
                if ("0".equals(jsonObject.getString("code"))) {
                    String data = jsonObject.getString("data");
                    GZ_TOKEN.put("ganzhou_tijian_token", data, 599995);
                    return data;
                } else {
                    throw new RuntimeException("获取token失败,接口返回:" + req);
                }

            } else {
                throw new RuntimeException("获取token失败！<CODE>" + httpSender.getCode() + "-->" + getRemoteError(httpSender));
            }
        }
    }

    /**
     * 获取token
     *
     * @return token
     */
    public String getToken(String type) {
        HttpSender httpSender = null;
        String token = "";
        String result;
        if (StringUtils.equals(StaticValues2.LZXZYY, type)) {
            //查询缓存
            String userCode;
            String userSecKey;
            String requestUrl;
            try {

                userSecKey = zyHospitalConfiguration.getLzxUserSecKey();
                userCode = zyHospitalConfiguration.getLzxUserName();

                requestUrl = zyHospitalConfiguration.getLzxIp()
                        + zyHospitalConfiguration.getLzxToken();
                // 生成HttpSender请求对象
                String url = "?userCode=" + userCode
                        + "&userSecKey=" + userSecKey + "&channer=cloud";
                LOGGER.info(type + "健康医疗获取token接口 " + url);
                httpSender = HttpSender.getInstanceGet(requestUrl + url);
                HttpUtil.addRequestHeader(httpSender);
                if (httpSender.execute()) {
                    result = httpSender.getResult();
                    LOGGER.info(type + "健康医疗 - " + url + "接口请求三方反参：" + result);
                    JSONObject response = JSON.parseObject(result);
                    Boolean ret = response.getBoolean("ret");
                    if (!ret) {
                        LOGGER.error("返回状态为失败！" + JsonUtil.objectToString(result));
                        throw new OperationException(response.getString("msg"));
                    }
                    JSONObject dataObject = response.getJSONObject("data");
                    token = dataObject.getString("token");
                    if (StringUtil.isEmpty(token)) {
                        throw new OperationException("获取token失败！反参信息为：" + result);
                    }
                    Integer expiresMin = dataObject.getInteger("expiresMin");
                    int seconds = expiresMin * 60 - 15;

                } else {
                    throw new RuntimeException(getRemoteError(httpSender));
                }
            } catch (Exception e) {
                LOGGER.info(type + "健康医疗获取token接口异常：" + e.getMessage());
            } finally {
                if (null != httpSender) {
                    httpSender.close();
                }
            }
            return token;
        } else if (StringUtils.equals(StaticValues2.MLYY, type)) {
            String userCode;
            String userSecKey;
            String requestUrl;
            try {

                userSecKey = zyHospitalConfiguration.getMlUserSecKey();
                userCode = zyHospitalConfiguration.getMlUserName();

                requestUrl = zyHospitalConfiguration.getMlIp()
                        + zyHospitalConfiguration.getLzxToken();
                // 生成HttpSender请求对象
                String url = "?userCode=" + userCode
                        + "&userSecKey=" + userSecKey + "&channer=cloud";
                LOGGER.info(type + "健康医疗获取token接口 " + url);
                httpSender = HttpSender.getInstanceGet(requestUrl + url);
                HttpUtil.addRequestHeader(httpSender);
                if (httpSender.execute()) {
                    result = httpSender.getResult();
                    LOGGER.info(type + "健康医疗 - " + url + "接口请求三方反参：" + result);
                    JSONObject response = JSON.parseObject(result);
                    Boolean ret = response.getBoolean("ret");
                    if (!ret) {
                        LOGGER.error("返回状态为失败！" + JsonUtil.objectToString(result));
                        throw new OperationException(response.getString("msg"));
                    }
                    JSONObject dataObject = response.getJSONObject("data");
                    token = dataObject.getString("token");
                    if (StringUtil.isEmpty(token)) {
                        throw new OperationException("获取token失败！反参信息为：" + result);
                    }
                    Integer expiresMin = dataObject.getInteger("expiresMin");
                    int seconds = expiresMin * 60 - 15;

                } else {
                    throw new RuntimeException(getRemoteError(httpSender));
                }
            } catch (Exception e) {
                LOGGER.info(type + "健康医疗获取token接口异常：" + e.getMessage());
            } finally {
                if (null != httpSender) {
                    httpSender.close();
                }
            }
            return token;
        } else if (StringUtils.equals(StaticValues2.GTYY, type)) {
            String userCode;
            String userSecKey;
            String requestUrl;
            try {

                userSecKey = zyHospitalConfiguration.getGtUserSecKey();
                userCode = zyHospitalConfiguration.getGtUserName();

                requestUrl = zyHospitalConfiguration.getGtIp()
                        + zyHospitalConfiguration.getLzxToken();
                // 生成HttpSender请求对象
                String url = "?userCode=" + userCode
                        + "&userSecKey=" + userSecKey + "&channer=cloud";
                LOGGER.info(type + "健康医疗获取token接口 " + url);
                httpSender = HttpSender.getInstanceGet(requestUrl + url);
                HttpUtil.addRequestHeader(httpSender);
                if (httpSender.execute()) {
                    result = httpSender.getResult();
                    LOGGER.info(type + "健康医疗 - " + url + "接口请求三方反参：" + result);
                    JSONObject response = JSON.parseObject(result);
                    Boolean ret = response.getBoolean("ret");
                    if (!ret) {
                        LOGGER.error("返回状态为失败！" + JsonUtil.objectToString(result));
                        throw new OperationException(response.getString("msg"));
                    }
                    JSONObject dataObject = response.getJSONObject("data");
                    token = dataObject.getString("token");
                    if (StringUtil.isEmpty(token)) {
                        throw new OperationException("获取token失败！反参信息为：" + result);
                    }
                    Integer expiresMin = dataObject.getInteger("expiresMin");
                    int seconds = expiresMin * 60 - 15;

                } else {
                    throw new RuntimeException(getRemoteError(httpSender));
                }
            } catch (Exception e) {
                LOGGER.info(type + "健康医疗获取token接口异常：" + e.getMessage());
            } finally {
                if (null != httpSender) {
                    httpSender.close();
                }
            }
            return token;
        } else if (StringUtils.equals(StaticValues2.GTZYY, type)) {
            String userCode;
            String userSecKey;
            String requestUrl;
            try {

                userSecKey = zyHospitalConfiguration.getGtzyyUserSecKey();
                userCode = zyHospitalConfiguration.getGtzyyUserName();

                requestUrl = zyHospitalConfiguration.getGtzyyIp()
                        + zyHospitalConfiguration.getLzxToken();
                // 生成HttpSender请求对象
                String url = "?userCode=" + userCode
                        + "&userSecKey=" + userSecKey + "&channer=cloud";
                LOGGER.info(type + "健康医疗获取token接口 " + url);
                httpSender = HttpSender.getInstanceGet(requestUrl + url);
                HttpUtil.addRequestHeader(httpSender);
                if (httpSender.execute()) {
                    result = httpSender.getResult();
                    LOGGER.info(type + "健康医疗 - " + url + "接口请求三方反参：" + result);
                    JSONObject response = JSON.parseObject(result);
                    Boolean ret = response.getBoolean("ret");
                    if (!ret) {
                        LOGGER.error("返回状态为失败！" + JsonUtil.objectToString(result));
                        throw new OperationException(response.getString("msg"));
                    }
                    JSONObject dataObject = response.getJSONObject("data");
                    token = dataObject.getString("token");
                    if (StringUtil.isEmpty(token)) {
                        throw new OperationException("获取token失败！反参信息为：" + result);
                    }
                    Integer expiresMin = dataObject.getInteger("expiresMin");
                    int seconds = expiresMin * 60 - 15;

                } else {
                    throw new RuntimeException(getRemoteError(httpSender));
                }
            } catch (Exception e) {
                LOGGER.info(type + "健康医疗获取token接口异常：" + e.getMessage());
            } finally {
                if (null != httpSender) {
                    httpSender.close();
                }
            }
            return token;
        }
        return token;
    }

    public static void main(String[] args) {
        Sm2Key sm2Key = SecurityUtil.createSm2Key();

        System.out.println(sm2Key.getPrivateKey());
        System.out.println(sm2Key.getPublicKey());
    }
}
