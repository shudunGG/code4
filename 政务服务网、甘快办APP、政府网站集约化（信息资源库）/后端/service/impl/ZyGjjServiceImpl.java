package com.hanweb.yyjr.zhangye.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hanweb.common.util.HttpSender;
import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.json.Type;
import com.hanweb.common.util.log.LogWriter;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.security.SecurityUtil;
import com.hanweb.gsszf.bean.*;
import com.hanweb.gsszf.constant.ProjectConstants;
import com.hanweb.gsszf.dto.dx.BodyParamDto;
import com.hanweb.gsszf.dto.zy.*;
import com.hanweb.gsszf.member.IndividualsAndCorporation;
import com.hanweb.gsszf.util.SharedSwitchingPlatformUtil;
import com.hanweb.yyjr.dignxi.util.ParamValidationUtil;
import com.hanweb.yyjr.member.service.LoginService;
import com.hanweb.yyjr.zhangye.service.ZyGjjService;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sviolet.smcrypto.SmCryptoUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zds
 * @className ZyGjjServiceImpl
 * @description 张掖市公积金service实现类
 * @date: 2022/3/31 9:42
 */
@Service
public class ZyGjjServiceImpl implements ZyGjjService {

    @Autowired
    ZysProjectProperties zysProjectProperties;

    @Autowired
    DxProjectProperties dxProjectProperties;

    private static final String MSG_STR1 = "http调用张掖市公积金接口";

    @Autowired
    private LoginService loginService;

    @Value("${project.ww.publicKey:123}")
    private String publicKey;

    @Value("${project.ww.privateKey:123}")
    private String privateKey;


    /**
     * 返回参数加密
     */
    private String sm2PublicKey = "04474351a080cd5d6297db9fe2ce7d693be0320d739c93acd79188c416d5a9495063d59e963c2644b2c2e007473069185ce3825930edb6db21135833a6d918fdcc";


    /**
     * 张掖市公积金中转接口入口
     *
     * @param result    结果集
     * @param bodyParam 请求体json
     * @return
     */
    @Override
    public JsonResult execute(JsonResult result, String bodyParam) {
        LogWriter.info("张掖市公积金中转接口入参bodyParam-->" + bodyParam);
        // 接口标识
        String pathParam;
        BodyParamDto bodyParamDto;
        try {
            // 获取加密参数
            bodyParam = JSON.parseObject(bodyParam).getString("param_code");
            // 请求体解密
            bodyParam = SmCryptoUtil.sm2Decode(bodyParam, zysProjectProperties.getPrivateKey());
            LogWriter.info("张掖市公积金中转接口解密后bodyParam-->" + bodyParam);
            if (StringUtil.isEmpty(bodyParam)) {
                return result.setCode(ReturnEnum.RT503.getCode()).setSuccess(ReturnEnum.RT503.isSuccess())
                        .setMessage(ReturnEnum.RT503.getMsg());
            }

            //转为实体
            bodyParamDto = JsonUtil.StringToObject(bodyParam, BodyParamDto.class);

            //越权校验开始
            Map<String, String> formParam = bodyParamDto.getFormParam();

            if (CollectionUtil.isNotEmpty(formParam)){
                //获取请求体的身份证号
                String sfzh = formParam.get("sfzh");
                //获取省统一身份认证的token值
                String token = formParam.get("token");
                //解密
                token = loginService.sm2DecodeToken(token);
                //获取省统一身份认证的authCode值
                String authCode = formParam.get("authCode");
                authCode = loginService.sm2DecodeAuthCode(authCode);
                //当有身份证号的应用请求，则进行越权校验
                if (StrUtil.isNotBlank(sfzh)){
                    //非空校验
                    if (StrUtil.isNotBlank(token)){
                        IndividualsAndCorporation member = new IndividualsAndCorporation();
                        //获取用户信息
                        member = loginService.transformUserInfoBean(authCode, member, token);
                        //获取当前登录的用户身份证号
                        String cardNo = member.getCardNo();
                        LogWriter.debug(member+":"+cardNo);
                        //是否越权校验
                        if (!StringUtil.equals(cardNo,sfzh)) {
                            return result.setCode(ReturnEnum.RT517.getCode()).setSuccess(ReturnEnum.RT517.isSuccess())
                                    .setMessage(ReturnEnum.RT517.getMsg());
                        }
                    }else {
                        return result.setCode(ReturnEnum.RT516.getCode()).setSuccess(ReturnEnum.RT516.isSuccess())
                                .setMessage(ReturnEnum.RT516.getMsg());
                    }
                }
            }else {
                return result.setCode(ReturnEnum.RT400.getCode()).setSuccess(ReturnEnum.RT400.isSuccess())
                        .setMessage(ReturnEnum.RT400.getMsg());
            }
            //越权校验结束

            // 根据接口标识pathParam来判断用哪个bean验参
            pathParam = bodyParamDto.getPathParam();
            if (StringUtil.isEmpty(pathParam)) {
                return result.setCode(ReturnEnum.RT400.getCode()).setSuccess(ReturnEnum.RT400.isSuccess())
                        .setMessage(ReturnEnum.RT400.getMsg());
            }
            switch (pathParam) {
                //  公积金合作楼盘查询
                case "hzlpList":
                    bodyParam = this.isQueryBuilding(bodyParam, result, bodyParamDto);
                    break;
                //  公积金个人基本信息查询
                case "grgjjjbxxcx":
                    //  公积金缴存信息查询
                case "gjjjcxxcx":
                    //  公积金查询
                case "gjjcx":
                    //  基本信息查询
                case "gjjjbxxcx":
                    //  公积金账户信息
                case "gjjzhxxcx":
                    bodyParam = this.isQueryCardAndName(bodyParam, result, bodyParamDto);
                    break;
                //  公积金还贷记录查询
                case "dkjvList":
                    bodyParam = this.isQueryByLoanAccount(bodyParam, result, bodyParamDto);
                    break;
                //  公积金缴存记录查询
                case "gjjjcjl":
                    //  公积金提取记录查询
                case "gjjtqjl":
                    //  公积金个人明细查询
                case "grmxList":
                    bodyParam = this.isQueryByAccount(bodyParam, result, bodyParamDto);
                    break;
                //  公积金缴存证明查询
                case "gjjPayment":
                    //  公积金异地贷款证明查询
                case "getYddkzgjcsyzm":
                    //  公积金贷款信息查询
                case "gjjdkxxList":
                    //  公积金贷款进度查询
                case "dkjdList":
                    //  贷款进度查询
                case "dkjdCxList":
                    //  贷款信息查询
                case "dkxxList":
                    //  公积金贷款查询
                case "gjjdkList":
                    //  公积金提取进度查询
                case "gjjtqjdList":
                    bodyParam = this.isQueryByCard(bodyParam, result, bodyParamDto);
                    break;
                //  公积金中心大厅地址查询
                case "zxdzList":
                    bodyParam = this.isQueryAddress(bodyParam, result, bodyParamDto);
                    break;
                //  公积金缴存证明打印
                case "zgjczmPdf":
                    //  通过证件号码下载异地贷款缴存证明
                case "yddkzgjcsyzmDown":
                    bodyParam = this.isQueryDownLoad(bodyParam, result, bodyParamDto);
                    break;
                // 接口未对接时置为未注册
                default:
                    bodyParam = "unregistered";
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogWriter.error(e.getMessage());
            return result.setCode(ReturnEnum.RT502.getCode()).setSuccess(ReturnEnum.RT502.isSuccess())
                    .setMessage(ReturnEnum.RT502.getMsg());
        }
        return this.httpExecute(result, pathParam, bodyParam, bodyParamDto);
    }

    /**
     * 公积金缴存证明打印和异通过证件号码下载异地贷款缴存证明入口
     *
     * @param result    结果集
     * @param bodyParam 请求体json
     * @param response  响应
     * @return
     */
    @Override
    public Object downLoadProve(JsonResult result, String bodyParam, HttpServletResponse response) {
        LogWriter.info("张掖公积金下载接口入参bodyParam-->" + bodyParam);
        // 接口标识
        String pathParam;
        BodyParamDto bodyParamDto;
        try {
            // 请求体解密
            bodyParam = SmCryptoUtil.sm2Decode(bodyParam, zysProjectProperties.getPrivateKey());
            LogWriter.info("张掖公积金中转接口解密后bodyParam-->" + bodyParam);
            if (StringUtil.isEmpty(bodyParam)) {
                return result.setCode(ReturnEnum.RT503.getCode()).setSuccess(ReturnEnum.RT503.isSuccess())
                        .setMessage(ReturnEnum.RT503.getMsg());
            }

            //转为实体
            bodyParamDto = JsonUtil.StringToObject(bodyParam, BodyParamDto.class);

            // 根据接口标识pathParam来判断用哪个bean验参
            pathParam = bodyParamDto.getPathParam();
            if (StringUtil.isEmpty(pathParam)) {
                return result.setCode(ReturnEnum.RT400.getCode()).setSuccess(ReturnEnum.RT400.isSuccess())
                        .setMessage(ReturnEnum.RT400.getMsg());
            }
            switch (pathParam) {
                //  公积金缴存证明打印
                case "zgjczmPdf":
                    //  通过证件号码下载异地贷款缴存证明
                case "yddkzgjcsyzmDown":
                    bodyParam = this.isQueryDownLoad(bodyParam, result, bodyParamDto);
                    break;
                // 接口未对接时置为未注册
                default:
                    bodyParam = "unregistered";
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            LogWriter.error(e.getMessage());
            return result.setCode(ReturnEnum.RT502.getCode()).setSuccess(ReturnEnum.RT502.isSuccess())
                    .setMessage(ReturnEnum.RT502.getMsg());
        }
        return this.downLoadExecute(result, pathParam, bodyParam, response, bodyParamDto);
    }

    /**
     * 公积金证明下载
     *
     * @param result    结果集
     * @param pathParam 报文交易代码
     * @param bodyParam 请求体参数
     * @return
     */
    private Object downLoadExecute(JsonResult result, String pathParam, String bodyParam, HttpServletResponse response, BodyParamDto bodyParamDto) {
        LogWriter.info("张掖公积金证明下载transcode-->" + pathParam);
        if (StringUtil.isEmpty(result.getMessage())) {
            result.setCode(ReturnEnum.RT506.getCode()).setMessage(ReturnEnum.RT506.getMsg())
                    .setSuccess(ReturnEnum.RT506.isSuccess());
        }
        // 判断接口是否未注册
        if (StringUtil.equals(ProjectConstants.UNREGISTERED, bodyParam)) {
            return result.setCode(ReturnEnum.RT507.getCode()).setMessage(ReturnEnum.RT507.getMsg())
                    .setSuccess(ReturnEnum.RT507.isSuccess());
        }
        if (StringUtil.isNotEmpty(bodyParam)) {
            // 初始化参数
            HttpSender sender = null;
            try {
                HttpSenderParam httpSenderParam = initParam(pathParam, bodyParamDto);
                sender = initHttp(httpSenderParam);
                //证明下载
                if (!sender.execute()) {
                    result.setCode(ReturnEnum.RT500.getCode()).setSuccess(ReturnEnum.RT500.isSuccess())
                            .setMessage(ReturnEnum.RT500.getMsg());
                    return result;
                }
                byte[] bytes = sender.getByte();

                //pdf下载
                BufferedOutputStream output;
                // 浏览器下载
                output = new BufferedOutputStream(response.getOutputStream());
                // 文件命名
                String fileName = "";
                switch (pathParam) {
                    case "zgjczmPdf":
                        fileName = "公积金缴存证明.pdf";
                        break;
                    case "yddkzgjcsyzmDown":
                        fileName = "异地贷款缴存证明.pdf";
                        break;
                    default:
                }
                String fileNameDown = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
                LogWriter.info("fileName===" + fileNameDown);
                //fileNameDown上面得到的文件名
                response.setHeader("Content-Disposition", "attachment;filename=" +
                        fileNameDown);
                output.write(bytes);
                response.flushBuffer();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
                LogWriter.error(e.getMessage());
                result.setCode(ReturnEnum.RT505.getCode()).setSuccess(ReturnEnum.RT505.isSuccess())
                        .setMessage(ReturnEnum.RT505.getMsg());
            } finally {
                if (null != sender){
                    sender.close();
                }
            }
        }
        return result;
    }


    /**
     * 下载文件初始化HttpSender
     *
     * @param httpSenderParam 请求常规参数封装类
     * @return
     */
    public HttpSender initHttp(HttpSenderParam httpSenderParam) {
        BizMethod bizMethod;
        bizMethod = zysProjectProperties.getBizMethod().get(httpSenderParam.getBizMethodKey());
        // 从配置文件中获取到三方服务接口地址
        String url = bizMethod.getUrl();
        HttpSender httpSender;
        httpSender = HttpSender.getInstancePost(url);
        // 设置http请求的基本参数
        httpSender.setConnectTimeOut(dxProjectProperties.getConnectTimeOut());
        httpSender.setReadTimeOut(dxProjectProperties.getReadTimeOut());
        // 获取连接类型和请求头
        String contentType = httpSenderParam.getContentType();
        Map<String, Object> headers = httpSenderParam.getHeaders();
        // 设置连接类型
        if (StringUtil.isNotEmpty(contentType)) {
            httpSender.addHeader("Content-Type", contentType);
        }
        // 设置请求头
        if (headers != null) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                if (entry.getValue() != null) {
                    httpSender.addHeader(entry.getKey(), entry.getValue().toString());
                }
            }
        }
        String jsonParam = httpSenderParam.getJsonParam();
        if (StringUtil.isNotEmpty(jsonParam)) {
            httpSender.setRequestBody(new StringEntity(jsonParam, StandardCharsets.UTF_8));
        }
        return httpSender;
    }


    /**
     * 共享平台接口调用
     *
     * @param result    结果集
     * @param pathParam 接口路径参数
     * @param bodyParam 请求体参数
     * @return
     */
    private JsonResult httpExecute(JsonResult result, String pathParam, String bodyParam, BodyParamDto bodyParamDto) {
        if (StringUtil.isEmpty(result.getMessage())) {
            result.setCode(ReturnEnum.RT506.getCode()).setMessage(ReturnEnum.RT506.getMsg())
                    .setSuccess(ReturnEnum.RT506.isSuccess());
        }
        // 判断接口是否未注册
        if (StringUtil.equals(ProjectConstants.UNREGISTERED, bodyParam)) {
            return result.setCode(ReturnEnum.RT507.getCode()).setMessage(ReturnEnum.RT507.getMsg())
                    .setSuccess(ReturnEnum.RT507.isSuccess());
        }
        if (StringUtil.isNotEmpty(bodyParam)) {
            // 初始化参数
            HttpSenderParam httpSenderParam = initParam(pathParam, bodyParamDto);
            // 发送http请求
            String resultStr = SharedSwitchingPlatformUtil.httpSenderExecute(result, httpSenderParam, MSG_STR1, ProjectConstants.STR_4);
            if (StringUtil.isEmpty(resultStr)) {
                return result;
            }
            switch (pathParam) {
                //  公积金缴存证明打印
                case "zgjczmPdf":
                    //  通过证件号码下载异地贷款缴存证明
                case "yddkzgjcsyzmDown":
                    return result.setCode(ReturnEnum.RT200.getCode()).addData("retData", resultStr).setMessage(ReturnEnum.RT200.getMsg());
                default:
            }
            manage(resultStr, result);
        }
        return result;
    }

    /**
     * 返参处理
     *
     * @param resultStr 三方接口数据
     * @param result    结果集
     */
    private void manage(String resultStr, JsonResult result) {

        //将返回的数据进行解析
        JSONObject responseBodyJson = JSON.parseObject(resultStr);
        //获取加密的数据体和数据签名
        String responseCryptoData = responseBodyJson.getString("cryptoData");
        SM2 sm2jiemi = SmUtil.sm2(Base64.decode(zysProjectProperties.getOtherPrivateKey()), Base64.decode(zysProjectProperties.getOtherPublicKey()));
        //使用私钥进行解密
        String responseData = sm2jiemi.decryptStr(responseCryptoData, KeyType.PrivateKey);

        LogWriter.info("张掖市公积金返回的解密数据-->" + responseData);
        JSONObject jsonObject = JSON.parseObject(responseData);
        String code = jsonObject.getJSONObject("result").getString("code");
        if (StringUtils.equals(ProjectConstants.STR_00, code)) {
            //返回参数加密开始
            result.addData("data",SecurityUtil.sm2Encode(responseData, sm2PublicKey));
            result.setCode(ReturnEnum.RT200.getCode()).setMessage(ReturnEnum.RT200.getMsg());
            result.setSuccess(true);
        } else {
            result.setCode(ReturnEnum.RT510.getCode()).setMessage(jsonObject.getJSONObject("result").getString(ProjectConstants.MSG));
        }

    }

    /**
     * 下载验证
     *
     * @param bodyParam    请求体对象
     * @param result       结果集
     * @param bodyParamDto 接口入参dto
     * @return
     */
    public String isQueryDownLoad(String bodyParam, JsonResult result, BodyParamDto bodyParamDto) {
        Map<String, String> param = getParam(bodyParamDto);
        // 转化dto类
        ZyCardDownLoadDto dto = JsonUtil.StringToObject(JSON.toJSONString(param), ZyCardDownLoadDto.class);
        return this.checkBodyStr(bodyParam, dto, result);
    }

    /**
     * 中心大厅地址查询
     *
     * @param bodyParam    请求体对象
     * @param result       结果集
     * @param bodyParamDto 接口入参dto
     * @return
     */
    public String isQueryAddress(String bodyParam, JsonResult result, BodyParamDto bodyParamDto) {
        Map<String, String> param = getParam(bodyParamDto);
        // 转化dto类
        ZyAddressDto dto = JsonUtil.StringToObject(JSON.toJSONString(param), ZyAddressDto.class);
        return this.checkBodyStr(bodyParam, dto, result);
    }

    /**
     * 通过身份证和姓名查询
     *
     * @param bodyParam    请求体对象
     * @param result       结果集
     * @param bodyParamDto 接口入参dto
     * @return
     */
    public String isQueryCardAndName(String bodyParam, JsonResult result, BodyParamDto bodyParamDto) {
        Map<String, String> param = getParam(bodyParamDto);
        // 转化dto类
        ZyCardAndNameDto dto = JsonUtil.StringToObject(JSON.toJSONString(param), ZyCardAndNameDto.class);
        return this.checkBodyStr(bodyParam, dto, result);
    }

    /**
     * 通过贷款账号查询
     *
     * @param bodyParam    请求体对象
     * @param result       结果集
     * @param bodyParamDto 接口入参dto
     * @return
     */
    public String isQueryByLoanAccount(String bodyParam, JsonResult result, BodyParamDto bodyParamDto) {
        Map<String, String> param = getParam(bodyParamDto);
        // 转化dto类
        ZyLoanAccountDto dto = JsonUtil.StringToObject(JSON.toJSONString(param), ZyLoanAccountDto.class);
        return this.checkBodyStr(bodyParam, dto, result);
    }

    /**
     * 通过楼盘信息查询
     *
     * @param bodyParam    请求体对象
     * @param result       结果集
     * @param bodyParamDto 接口入参dto
     * @return
     */
    public String isQueryBuilding(String bodyParam, JsonResult result, BodyParamDto bodyParamDto) {
        Map<String, String> param = getParam(bodyParamDto);
        // 转化dto类
        ZyBuildingDto dto = JsonUtil.StringToObject(JSON.toJSONString(param), ZyBuildingDto.class);
        return this.checkBodyStr(bodyParam, dto, result);
    }

    /**
     * 通过个人账户查询
     *
     * @param bodyParam    请求体对象
     * @param result       结果集
     * @param bodyParamDto 接口入参dto
     * @return
     */
    public String isQueryByAccount(String bodyParam, JsonResult result, BodyParamDto bodyParamDto) {
        Map<String, String> param = getParam(bodyParamDto);
        // 转化dto类
        ZyAccountDto dto = JsonUtil.StringToObject(JSON.toJSONString(param), ZyAccountDto.class);
        return this.checkBodyStr(bodyParam, dto, result);
    }

    /**
     * 通过身份证查询下
     *
     * @param bodyParam    请求体对象
     * @param result       结果集
     * @param bodyParamDto 接口入参dto
     * @return
     */
    public String isQueryByCard(String bodyParam, JsonResult result, BodyParamDto bodyParamDto) {
        Map<String, String> param = getParam(bodyParamDto);
        // 转化dto类
        ZyCardDto dto = JsonUtil.StringToObject(JSON.toJSONString(param), ZyCardDto.class);
        return this.checkBodyStr(bodyParam, dto, result);
    }


    /**
     * 根据请求方式取出业务参数
     *
     * @param bodyParamDto 接口入参dto
     * @return
     */
    private Map<String, String> getParam(BodyParamDto bodyParamDto) {
        //根据请求方式取出业务参数
        String method = zysProjectProperties.getBizMethod().get(bodyParamDto.getPathParam()).getMethod();
        Map<String, String> param;
        if (StringUtil.equals(method, ProjectConstants.GET)) {
            param = bodyParamDto.getUrlParam();
        } else {
            param = bodyParamDto.getFormParam();
        }
        return param;
    }


    /**
     * 初始化请求参数
     *
     * @param pathParam    路径参数
     * @param bodyParamDto 接口入参dto
     * @return
     */
    private HttpSenderParam initParam(String pathParam, BodyParamDto bodyParamDto) {
        HttpSenderParam httpSenderParam = new HttpSenderParam();
        //组装头部数据
        Map<String, Object> head = new HashMap<>(ProjectConstants.INT_16);
        //中心组织的ID
        head.put("groupId", zysProjectProperties.getGroupId());
        //系統时间戳
        head.put("timestamp", System.currentTimeMillis());
        //生成的32位随机UUID
        head.put("reqId", StringUtil.getUUIDString());
        //组装业务数据
        Map<String, Object> body = new HashMap<>(ProjectConstants.INT_16);
        Map<String, String> param = this.getParam(bodyParamDto);
        param.put("xxly", ProjectConstants.STR_4);
        // 配置http请求参数
        body.put("head", head);
        body.put("data", param);
        //加密处理
        String bodyStr = JSON.toJSONString(body);
        LogWriter.info("张掖公积金加密前的业务参数===" + bodyStr);
        //组装sm2
        LogWriter.info("张掖公积金加密公钥"+zysProjectProperties.getOtherPublicKey());
        LogWriter.info("张掖公积金解密私钥"+zysProjectProperties.getOtherPrivateKey());
        SM2 sm2 = SmUtil.sm2(Base64.decode(zysProjectProperties.getOtherPrivateKey()), Base64.decode(zysProjectProperties.getOtherPublicKey()));
        //使用银行的公钥进行加密
        String cryptoData = sm2.encryptBcd(bodyStr, KeyType.PublicKey);
        //使用自己的私钥进行签名后进行HEX
        String sign = HexUtil.encodeHexStr(sm2.sign(bodyStr.getBytes(StandardCharsets.UTF_8)));
        //封装加密后的数据体
        Map<String, Object> encodeBody = new HashMap<>(ProjectConstants.INT_16);
        encodeBody.put("cryptoData", cryptoData);
        encodeBody.put("sign", sign);
        //最终数据转化为JSON字符串
        String jsonBody = JSON.toJSONString(encodeBody);
        LogWriter.info("加密后的数据===" + jsonBody);
        httpSenderParam.setJsonParam(jsonBody);
        head = new HashMap<>(ProjectConstants.INT_16);
        head.put("GROUP_ID", zysProjectProperties.getGroupId());
        httpSenderParam.setHeaders(head);
        httpSenderParam.setParamType(ProjectConstants.STR_1);
        httpSenderParam.setContentType(ProjectConstants.STR_JSON);
        httpSenderParam.setRequestType(ProjectConstants.POST);
        httpSenderParam.setBizMethodKey(pathParam);
        return httpSenderParam;
    }


    /**
     * 效验必填参数并返回请求体
     *
     * @param bodyParam 请求体
     * @param obj       待检dto类
     * @param result    结果集
     * @return
     */
    private String checkBodyStr(String bodyParam, Object obj, JsonResult result) {
        // 校验必填参数
        if (obj == null || Boolean.FALSE.equals(ParamValidationUtil.variableValidation(obj, result))) {
            bodyParam = "";
        }
        return bodyParam;
    }


}
