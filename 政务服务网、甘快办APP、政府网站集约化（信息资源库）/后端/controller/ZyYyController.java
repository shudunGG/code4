package com.hanweb.yyjr.zhangye.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.log.LogWriter;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.gsszf.member.IndividualsAndCorporation;
import com.hanweb.yyjr.member.service.LoginService;
import com.hanweb.yyjr.tianshui.utils.XmlUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Map;

/**
 * @version 1.0.0
 * @title ZyYyController
 * @Description 张掖第二医院对接
 * @Date 2024/4/25 10:42
 * @Author hao
 **/
@Api(tags = "张掖第二医院对接相关接口")
@RestController
@RequestMapping("interface/zy-yy")
public class ZyYyController {

    /**
     * 查询密钥
     */
    @Value("${project.zyyy.checkCode:845246542313549875416546546541321657498746276834556331230165}")
    private String checkCode;

    /**
     * 张掖第二医院体检查询
     */
    @Value("${project.zyyy.webserviceUrl:http://125.75.154.38:8446/webservice/n_webservice.asmx}")
    private String webserviceUrl;
    @Autowired
    private LoginService loginService;


    /**
     * 张掖第二医院体检报告查询
     * @param token token获取用户信息使用
     * @param authCode 获取用户信息使用
     * @return
     */
    @PostMapping("/getBodyReport")
    @ResponseBody
    public JsonResult getBodyReport(String token,String authCode){
        JsonResult jsonResult = JsonResult.getInstance();
        if (StringUtil.isEmpty(token) && StringUtil.isEmpty(authCode)) {
            jsonResult.setSuccess(false);
            jsonResult.setCode("400");
            jsonResult.setMessage("用户未登录");
            return jsonResult;
        }
        IndividualsAndCorporation member = new IndividualsAndCorporation();
        //获取用户信息
        member = loginService.transformUserInfoBean(authCode, member, token);

        String sfzh = member.getCardNo();
        LogWriter.info("解密的sfzh为:" + sfzh);
        if (StringUtil.isEmpty(sfzh)) {
            jsonResult.setSuccess(false);
            jsonResult.setMessage("获取用户信息身份证号为空");
            jsonResult.setCode("400");
            return jsonResult;
        }
        String param = getParam(sfzh);
        try{
            String response = soapPost(webserviceUrl,param);
            Map map = XmlUtils.xmlToMap(response);
            LogWriter.info("调用webservice结果="+map.toString());
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(map));
            String result = jsonObject.getJSONObject("Body")
                    .getJSONObject("of_netResponse").getString("of_netResult");
            Map mapResult = XmlUtils.xmlToMap(result);
            System.out.println(JSON.toJSONString(mapResult));
            jsonResult.setSuccess(true);
            jsonResult.setMessage("查询成功");
            jsonResult.setCode("200");
            jsonResult.addData("data",JSON.parseObject(JSON.toJSONString(mapResult)));
        }catch (Exception e){
            LogWriter.error("调用webservice异常！",e);
            jsonResult.setSuccess(false);
            jsonResult.setMessage("入参解密失败或必要参数为空");
            jsonResult.setCode("400");
        }
        return jsonResult;
    }

    /**
     * 下载附件
     * @param downloadMaterialUrl
     */
    @ApiOperation(value = "下载附件", notes = "下载附件", httpMethod = "GET")
    @GetMapping("download")
    public JsonResult download(String downloadMaterialUrl) {
        JsonResult jsonResult = JsonResult.getInstance();
        if (StringUtil.isEmpty(downloadMaterialUrl)) {
            jsonResult.setSuccess(false);
            jsonResult.setMessage("下载附件地址为空！！");
            jsonResult.setCode("400");
            return jsonResult;
        }
        String base64Str = encodePdfToBase64(downloadMaterialUrl);
        if (StringUtil.isEmpty(base64Str)) {
            jsonResult.setSuccess(false);
            jsonResult.setMessage("转换base64字符串失败，请检查数据源！！");
            jsonResult.setCode("400");
            return jsonResult;
        }else{
            jsonResult.setSuccess(true);
            jsonResult.setMessage("获取成功");
            jsonResult.setCode("200");
            jsonResult.addData("data",base64Str);
        }
        return jsonResult;
    }
    /**
     * 获取pad信息
     * @param pdfUrl
     * @return
     */
    private String encodePdfToBase64(String pdfUrl){
        String base64Str="";
        try {
            URL url = new URL(pdfUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            // 连接并获取输入流
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                LogWriter.error("下载附件失败"+ responseCode);
            }
            // 读取PDF文件内容
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            try (var inputStream = connection.getInputStream()) {
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            // 将字节转换为Base64编码的字符串
            byte[] pdfBytes = outputStream.toByteArray();
            base64Str = Base64.getEncoder().encodeToString(pdfBytes);
        } catch (IOException e) {
            LogWriter.error("下载附件失败",e);
        }
        return base64Str;
    }


    /**
     * 拼接入参
     * @param sfzh
     * @return
     */
    public String getParam(String sfzh){
        String param = "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
                "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <of_net xmlns=\"http://tempurl.org\">\n" +
                "      <ls_xml>{\"CHECK\":\""+checkCode+"\",\"SFZH\":\""+sfzh+"\"}</ls_xml>\n" +
                "    </of_net>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>";
        return param;
    }

    /**
     * 发送http post调用webservice
     * @param strUrl
     * @param requestSoapXml
     * @return
     */
    public String soapPost(String strUrl, String requestSoapXml) throws IOException {
        //第一步：创建服务地址，不是WSDL地址
        URL url = new URL(strUrl);
        //第二步：打开一个通向服务地址的连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //第三步：设置参数
        //3.1发送方式设置：POST必须大写
        connection.setRequestMethod("POST");
        //3.2设置数据格式：content-type
        connection.setRequestProperty("content-type", "text/xml;charset=utf-8");
        //3.3设置输入输出，因为默认新创建的connection没有读写权限，
        connection.setDoInput(true);
        connection.setDoOutput(true);
        //请求头必须设置SOAPAction
        connection.setRequestProperty("SOAPAction","http://tempurl.org/of_net");
        //第四步：组织SOAP数据，发送请求
        OutputStream os = connection.getOutputStream();
        //这个方法遇到一个大坑记录下，传输的数据编码根据实际情况改动
        os.write(requestSoapXml.getBytes());
        //第五步：接收服务端响应，打印
        int responseCode = connection.getResponseCode();
        StringBuilder sb = new StringBuilder();
        if (200 == responseCode) {
            //表示服务端响应成功
            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String temp = null;
            while (null != (temp = br.readLine())) {
                sb.append(temp);
            }
            is.close();
            isr.close();
            br.close();
        }else{
            LogWriter.error("调用webservice失败：服务器端返回HTTP code " + responseCode + "信息：" );
        }
        os.close();
        return sb.toString();
    }
}
