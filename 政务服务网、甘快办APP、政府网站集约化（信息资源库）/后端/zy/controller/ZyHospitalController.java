package com.hanweb.yyjr.zhangye.zy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hanweb.common.exception.BaseException;
import com.hanweb.common.util.HttpSender;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.log.LogWriter;
import com.hanweb.common.util.mvc.JsonModelResult;
import com.hanweb.common.util.security.SecurityUtil;
import com.hanweb.gsszf.bean.ZysProjectProperties;
import com.hanweb.gsszf.constant.ProjectConstants;
import com.hanweb.gsszf.constant.StaticValues;
import com.hanweb.gsszf.member.IndividualsAndCorporation;
import com.hanweb.yyjr.member.service.LoginService;
import com.hanweb.yyjr.zhangye.util.RsaUtil;
import com.zdww.utils.RSAUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


/**
 * @ClassName ZyHospitalController
 * @Description 张掖医院相关中转接口
 * @Author tgq
 * @Date: 2024/5/28 10:42
 */
@Api(tags = "张掖医院相关中转接口")
@RestController
@RequestMapping("interface/zyHospital")
public class ZyHospitalController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private ZysProjectProperties zysProjectProperties;

    /**
     * 用于解密的RSA私钥
     */
    private String RSAPrivateKey = "";

    private static final String RSAKEY = "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";

    /**
     * @description 张掖医院体检查询
     * @param token 用户在系统中的编码
     * @param authCode 用户在系统中的编码
     * @return com.hanweb.common.util.mvc.JsonModelResult<java.lang.Object>
     * @author tgq
     * @date: 2024/5/6 10:27
     */
    @ApiOperation(value = "张掖医院体检查询", notes = "张掖医院体检查询", httpMethod = "POST")
    @PostMapping(value = "queryReport")
    public JsonModelResult<Object> queryReport(String token,String authCode) {
        JsonModelResult<Object> jsonModelResult = new JsonModelResult<>();
        if (StringUtil.isEmpty(token) && StringUtil.isEmpty(authCode)) {
            jsonModelResult.setSuccess(false);
            jsonModelResult.setMessage("用户未登录");
            return jsonModelResult;
        }
        IndividualsAndCorporation member = new IndividualsAndCorporation();
        token = loginService.sm2DecodeToken(token);
        authCode = loginService.sm2DecodeAuthCode(authCode);
        member = loginService.transformUserInfoBean(authCode, member, token);
        if (member == null){
            jsonModelResult.setSuccess(false);
            jsonModelResult.setMessage("获取用户身份信息失败");
            return jsonModelResult;
        }
        String cardNo = member.getCardNo();
        // 构建查询URL
        String url = "http://125.74.222.25:19081/api/health-exam/health-exam-report";
        // 发送POST请求并返回结果
        JSONObject object = new JSONObject();
        object.put("cardNo", cardNo);
        JsonModelResult<Object> jsonModelResult1 = postFormDataProject(url, object);
        String s = JSON.toJSONString(jsonModelResult1.getData());
        StringBuilder stringBuilder = new StringBuilder(s);
        String s1 = stringBuilder.substring(1,stringBuilder.length()-1).replace("\\", "").toString();
        JSONObject returnObject = JSON.parseObject(s1);
        JSONObject data = returnObject.getJSONObject("data");
        String encryData = data.getString("encryData");
        String decrypt = "";
        try {
            decrypt = RSAUtil.decrypt(encryData, RSAPrivateKey);
        } catch (Exception e) {
            LogWriter.error("解密失败",e);
        }
        returnObject.put("data", decrypt);
        jsonModelResult.setData(returnObject);
        return jsonModelResult;
    }

    /**
     * @description 善诊前置机查询报告-查询报告列表
     * @param token 获取访问令牌
     * @param authCode 用户在系统中的编码
     * @return com.hanweb.common.util.mvc.JsonModelResult<java.lang.Object>
     * @author xzc
     * @date: 2024/5/29 10:27
     */
    @ApiOperation(value = "善诊前置机查询报告-查询报告列表", notes = "善诊前置机查询报告-查询报告列表", httpMethod = "POST")
    @PostMapping(value = "reportOrderList")
    public JsonModelResult<Object> reportOrderList(String token, String authCode) {
        JsonModelResult<Object> jsonModelResult = new JsonModelResult<>();
        if (StringUtil.isEmpty(token) || StringUtil.isEmpty(authCode)) {
            jsonModelResult.setSuccess(false);
            jsonModelResult.setMessage("必填参数不能为空");
            return jsonModelResult;
        }
        String cardNoDec = SecurityUtil.sm2Decode(token, zysProjectProperties.getPrivateKey());
        String phoneDec = SecurityUtil.sm2Decode(authCode, zysProjectProperties.getPrivateKey());
        if (StringUtil.isEmpty(cardNoDec) || StringUtil.isEmpty(phoneDec)) {
            jsonModelResult.setSuccess(false);
            jsonModelResult.setMessage("必填参数解密失败");
            return jsonModelResult;
        }
        String generateToken = generateToken(cardNoDec, phoneDec);
        if (StringUtil.isEmpty(generateToken)) {
            jsonModelResult.setSuccess(false);
            jsonModelResult.setMessage("~~获取到的token是空的~~");
            return jsonModelResult;
        }
        // 构建查询URL
        String url = "http://tj.gszytcm.com:8766/sj-bifrost-web/report/reportOrderList" + "?token=" + generateToken;
        HttpSender httpSender = null;
        String result = "";
        try {
            //构建请求
            httpSender = HttpSender.getInstanceGet(url);
            httpSender.setConnectTimeOut(20);
            if (!httpSender.execute()) {
                InputStream inputStream = httpSender.getInputStream();
                String testString = convert(inputStream, StandardCharsets.UTF_8);
                return jsonModelResult.setSuccess(false).setCode(String.valueOf(httpSender.getCode()))
                        .setMessage(url + ProjectConstants.STR_LOG_4 + ProjectConstants.STR_LOG_5 + testString);
            } else {
                //取出返回值
                result = httpSender.getResult();
                LogWriter.info(ProjectConstants.STR_LOG_1 + url + ProjectConstants.STR_LOG_7 + result);
            }
            JSONObject resultJson = JSON.parseObject(result);
            if (StaticValues.STR_200.equals(resultJson.getString("state"))){
                String r = resultJson.getString("result");
                return jsonModelResult.setSuccess(true).setCode(StaticValues.STR_200)
                        .setMessage("~~查询报告列表成功~~")
                        .setData(SecurityUtil.sm2Encode(r,zysProjectProperties.getPublicKey()));
            } else {
                return jsonModelResult.setSuccess(false).setCode(StaticValues.STR_500)
                        .setMessage("~~查询报告列表失败~~" + resultJson.getString(StaticValues.MESSAGE));
            }
        } catch (Exception e) {
            LogWriter.error("调用失败", e);
            LogWriter.error(ProjectConstants.STR_LOG_1 + url + ProjectConstants.STR_LOG_8, e);
            return jsonModelResult.setSuccess(false).setCode(StaticValues.STR_500)
                    .setMessage("~~查询报告列表异常~~" + e.getMessage());
        } finally {
            if (httpSender != null) {
                httpSender.close();
            }
        }
    }


    public String generateToken(String cardNo,String phone){
        String token = "";
        if (StringUtil.isEmpty(cardNo) && StringUtil.isEmpty(phone)) {
            return token;
        }
        JSONObject requestJson = new JSONObject();
        requestJson.put("examineeIcno",cardNo);
        requestJson.put("examineePhone",phone);
        String url = "http://tj.gszytcm.com:8766/sj-bifrost-web/report/generateToken";
        HttpSender httpSender = null;
        String result = "";
        try {
            //构建请求
            LogWriter.info(ProjectConstants.STR_LOG_1 + url + ProjectConstants.STR_LOG_2 + requestJson.toJSONString());
            httpSender = HttpSender.getInstancePost(url);

            HttpEntity httpEntity = new StringEntity(requestJson.toJSONString(), "utf-8");
            httpSender.addHeader("Content-Type", "application/json");
            httpSender.addHeader("charset", "utf-8");
            httpSender.setRequestBody(httpEntity);
            httpSender.setConnectTimeOut(20);
            if (!httpSender.execute()) {
                InputStream inputStream = httpSender.getInputStream();
                String testString = convert(inputStream, StandardCharsets.UTF_8);
                throw new BaseException("~~获取token接口异常" + testString + "~~~");
            } else {
                //取出返回值
                result = httpSender.getResult();
                LogWriter.info(ProjectConstants.STR_LOG_1 + url + ProjectConstants.STR_LOG_7 + result);
            }
            JSONObject resultJson = JSON.parseObject(result);
            if (StaticValues.STR_200.equals(resultJson.getString("state"))){
                token = resultJson.getString("result");
                return token;
            } else {
                return "";
            }
        } catch (Exception e) {
            LogWriter.error("调用失败", e);
            LogWriter.error(ProjectConstants.STR_LOG_1 + url + ProjectConstants.STR_LOG_8, e);
        } finally {
            if (httpSender != null) {
                httpSender.close();
            }
        }
        return token;
    }

    /**
     * @description 善诊前置机查询报告-查询报告详情
     * @return com.hanweb.common.util.mvc.JsonModelResult<java.lang.Object>
     * @author xzc
     * @date: 2024/5/29 10:27
     */
    @ApiOperation(value = "善诊前置机查询报告-查询报告详情", notes = "善诊前置机查询报告-查询报告详情", httpMethod = "POST")
    @PostMapping(value = "reportDetail")
    public JsonModelResult<Object> reportDetail(String token, String authCode, String serviceNumber) {
        JsonModelResult<Object> jsonModelResult = new JsonModelResult<>();
        if (StringUtil.isEmpty(token) || StringUtil.isEmpty(authCode) || StringUtil.isEmpty(serviceNumber)) {
            jsonModelResult.setSuccess(false);
            jsonModelResult.setMessage("必填参数不能为空");
            return jsonModelResult;
        }
        String cardNoDec = SecurityUtil.sm2Decode(token, zysProjectProperties.getPrivateKey());
        String phoneDec = SecurityUtil.sm2Decode(authCode, zysProjectProperties.getPrivateKey());
        if (StringUtil.isEmpty(cardNoDec) || StringUtil.isEmpty(phoneDec)) {
            jsonModelResult.setSuccess(false);
            jsonModelResult.setMessage("必填参数解密失败");
            return jsonModelResult;
        }
        String generateToken = generateToken(cardNoDec, phoneDec);
        if (StringUtil.isEmpty(generateToken)) {
            jsonModelResult.setSuccess(false).setMessage("~~获取到的token是空的~~").setCode(StaticValues.STR_500);
            return jsonModelResult;
        }
        // 构建查询URL
        String url = "http://tj.gszytcm.com:8766/sj-bifrost-web/report/detail";
        JSONObject requestJson = new JSONObject();
        requestJson.put("token",generateToken);
        requestJson.put("serviceNumber",serviceNumber);
        requestJson.put("examineeIcno",cardNoDec);
        HttpSender httpSender = null;
        String result = "";
        try {
            //构建请求
            LogWriter.info(ProjectConstants.STR_LOG_1 + url + ProjectConstants.STR_LOG_2 + requestJson.toJSONString());
            httpSender = HttpSender.getInstancePost(url);
            HttpEntity httpEntity = new StringEntity(requestJson.toJSONString(), "utf-8");
            httpSender.addHeader("Content-Type", "application/json");
            httpSender.addHeader("charset", "utf-8");
            httpSender.setRequestBody(httpEntity);
            httpSender.setConnectTimeOut(20);
            if (!httpSender.execute()) {
                InputStream inputStream = httpSender.getInputStream();
                String testString = convert(inputStream, StandardCharsets.UTF_8);
                return jsonModelResult.setSuccess(false).setCode(String.valueOf(httpSender.getCode()))
                        .setMessage(url + ProjectConstants.STR_LOG_4 + ProjectConstants.STR_LOG_5 + testString);
            } else {
                //取出返回值
                result = httpSender.getResult();
                LogWriter.info(ProjectConstants.STR_LOG_1 + url + ProjectConstants.STR_LOG_7 + result);
            }
            JSONObject resultJson = JSON.parseObject(result);
            if (StaticValues.STR_200.equals(resultJson.getString("state"))){
                String r = resultJson.getString("result");
                return jsonModelResult.setSuccess(true).setCode(StaticValues.STR_200)
                        .setMessage("~~报告详情查询成功~~")
                        .setData(SecurityUtil.sm2Encode(r,zysProjectProperties.getPublicKey()));
            } else {
                return jsonModelResult.setSuccess(false).setCode(StaticValues.STR_500)
                        .setMessage("~~报告详情查询失败~~" + resultJson.getString(StaticValues.MESSAGE));
            }
        } catch (Exception e) {
            LogWriter.error("调用失败", e);
            LogWriter.error(ProjectConstants.STR_LOG_1 + url + ProjectConstants.STR_LOG_8, e);
            return jsonModelResult.setSuccess(false).setCode(StaticValues.STR_500)
                    .setMessage("~~报告详情查询异常~~" + e.getMessage());
        } finally {
            if (httpSender != null) {
                httpSender.close();
            }
        }
    }

    /**
     * @description 张掖河附院体检查询接口
     * @param token 获取访问令牌
     * @param authCode 用户在系统中的编码
     * @return com.hanweb.common.util.mvc.JsonModelResult<java.lang.Object>
     * @author xzc
     * @date: 2024/5/29 10:27
     */
    @ApiOperation(value = "张掖河附院体检查询接口", notes = "张掖河附院体检查询接口", httpMethod = "POST")
    @PostMapping(value = "healthExamReport")
    public JsonModelResult<Object> healthExamReport(String token,String authCode,String isTest) {
        JsonModelResult<Object> jsonModelResult = new JsonModelResult<>();
        if (StringUtil.isEmpty(isTest)) {
            jsonModelResult.setSuccess(false);
            jsonModelResult.setMessage("必填参数不能为空");
            return jsonModelResult;
        }
        // 构建查询URL
        String url = "http://125.74.222.25:19081/api/health-exam/health-exam-report";
        JSONObject requestJson = new JSONObject();
        String cardNoDec = SecurityUtil.sm2Decode(isTest, zysProjectProperties.getPrivateKey());
        if (StringUtil.isNotEmpty(cardNoDec)){
            requestJson.put("cardNo",cardNoDec);
        } else {
            jsonModelResult.setSuccess(false);
            jsonModelResult.setMessage("参数解密不能为空");
            return jsonModelResult;
        }
        HttpSender httpSender = null;
        String result = "";
        try {
            //构建请求
            LogWriter.info(ProjectConstants.STR_LOG_1 + url + ProjectConstants.STR_LOG_2 + requestJson.toJSONString());
            httpSender = HttpSender.getInstancePost(url);
            HttpEntity httpEntity = new StringEntity(requestJson.toJSONString(), "utf-8");
            httpSender.addHeader("Content-Type", "application/json");
            httpSender.addHeader("charset", "utf-8");
            httpSender.setRequestBody(httpEntity);
            httpSender.setConnectTimeOut(20);
            if (!httpSender.execute()) {
                InputStream inputStream = httpSender.getInputStream();
                String testString = convert(inputStream, StandardCharsets.UTF_8);
                return jsonModelResult.setSuccess(false).setCode(String.valueOf(httpSender.getCode()))
                        .setMessage(url + ProjectConstants.STR_LOG_4 + ProjectConstants.STR_LOG_5 + testString);
            } else {
                //取出返回值
                result = httpSender.getResult();
                LogWriter.info(ProjectConstants.STR_LOG_1 + url + ProjectConstants.STR_LOG_7 + result);
            }
            JSONObject resultJson = JSON.parseObject(result);
            if (StaticValues.STR_200.equals(resultJson.getString("statusCode"))){
                String encryData = resultJson.getJSONObject(StaticValues.DATA).getString("encryData");
                String decStr = RsaUtil.decryptByPrivateKey(encryData,RSAKEY);
                return jsonModelResult.setSuccess(true).setCode(StaticValues.STR_200)
                        .setMessage("~~报告详情查询成功~~")
                        .setData(SecurityUtil.sm2Encode(decStr,zysProjectProperties.getPublicKey()));
            } else {
                return jsonModelResult.setSuccess(true).setCode(StaticValues.STR_200)
                        .setMessage("~~报告详情查询完成，暂无数据~~" + resultJson.getString(StaticValues.MESSAGE));
            }
        } catch (Exception e) {
            LogWriter.error("调用失败", e);
            LogWriter.error(ProjectConstants.STR_LOG_1 + url + ProjectConstants.STR_LOG_8, e);
            return jsonModelResult.setSuccess(false).setCode(StaticValues.STR_500)
                    .setMessage("~~报告详情查询异常~~" + e.getMessage());
        } finally {
            if (httpSender != null) {
                httpSender.close();
            }
        }
    }

    /**
     * post请求共方法
     *
     * @param url        请求url
     * @param jsonObject 请求参数
     * @return
     */
    public JsonModelResult<Object> postFormDataProject(String url, JSONObject jsonObject) {

        JsonModelResult<Object> jsonModelResult = JsonModelResult.getInstance();
        HttpSender httpSender = null;
        String result = "";
        try {
            //构建请求
            LogWriter.info(ProjectConstants.STR_LOG_1 + url + ProjectConstants.STR_LOG_2 + jsonObject.toJSONString());
            httpSender = HttpSender.getInstancePost(url);

            HttpEntity httpEntity = new StringEntity(jsonObject.toJSONString(), "utf-8");
            httpSender.addHeader("Content-Type", "application/json");
            httpSender.addHeader("charset", "utf-8");
            httpSender.setRequestBody(httpEntity);
            httpSender.setConnectTimeOut(20);
            if (!httpSender.execute()) {
                InputStream inputStream = httpSender.getInputStream();
                String testString = convert(inputStream, StandardCharsets.UTF_8);
                jsonModelResult.setSuccess(false).setCode(String.valueOf(httpSender.getCode()))
                        .setMessage(url + ProjectConstants.STR_LOG_4 + ProjectConstants.STR_LOG_5 + testString);
            } else {
                //取出返回值
                result = httpSender.getResult();
                LogWriter.info(ProjectConstants.STR_LOG_1 + url + ProjectConstants.STR_LOG_7 + result);
            }
            jsonModelResult.setSuccess(true).setCode(StaticValues.STR_200).setData(result);
        } catch (Exception e) {
            LogWriter.error("调用失败", e);
            LogWriter.error(ProjectConstants.STR_LOG_1 + url + ProjectConstants.STR_LOG_8, e);
            jsonModelResult.setSuccess(false).setCode(StaticValues.STR_500).setMessage(ProjectConstants.STR_LOG_1 + url + ProjectConstants.STR_LOG_8);
        } finally {
            if (httpSender != null) {
                httpSender.close();
            }
        }
        return jsonModelResult;
    }




    /**
     * 将返回结果流转换为字符串
     *
     * @param inputStream
     * @param charset
     * @return
     * @throws IOException
     */
    public static String convert(InputStream inputStream, Charset charset) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }



}
