package com.hanweb.yyjr.zhangye.util;

import com.hanweb.common.util.HttpSender;
import com.hanweb.common.util.log.LogWriter;
import org.apache.http.HttpEntity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public class HttpUtil {



    private HttpUtil() {
    }


    /**
     * 处理请求头
     *
     * @param httpSender 请求对象
     * @param entity     请求实体
     */
    public static HttpSender addRequestHeaders(HttpSender httpSender, HttpEntity entity) {
        httpSender.addHeader("Content-Type", "application/json");
        httpSender.addHeader("charset", "utf-8");
        httpSender.addHeader("hsPartyId", "hl230000");
        httpSender.setRequestBody(entity);
        httpSender.setConnectTimeOut(20);
        return httpSender;
    }

    /**
     * 处理请求头
     *
     * @param httpSender 请求对象
     * @param entity     请求实体
     */
    public static void addRequestHeader(HttpSender httpSender, HttpEntity entity) {
        httpSender.addHeader("Content-Type", "application/json;charset=utf-8");
        httpSender.setRequestBody(entity);
        httpSender.setConnectTimeOut(20);
        httpSender.setReadTimeOut(20);
    }

    public static void addRequestHeader(HttpSender httpSender) {
        httpSender.setConnectTimeOut(20);
        httpSender.setReadTimeOut(20);
        httpSender.addHeader("Content-Type", "application/json;charset=utf-8");
    }

    public static void addRequestHeaderToken(HttpSender httpSender, String token) {
        httpSender.setConnectTimeOut(20);
        httpSender.setReadTimeOut(20);
        httpSender.addHeader("Content-Type", "application/json;charset=utf-8");
        httpSender.addHeader("Authorization", token);
    }

    public static void addBodyRequestHeaderToken(HttpSender httpSender, String token, HttpEntity entity) {
        httpSender.setConnectTimeOut(20);
        httpSender.setReadTimeOut(20);
        httpSender.setRequestBody(entity);
        httpSender.addHeader("Content-Type", "application/json;charset=utf-8");
        httpSender.addHeader("Authorization", "Bearer "+token);
    }

    public static void addRequestHeader2(HttpSender httpSender, HttpEntity entity) {
        httpSender.addHeader("Content-Type", "application/json");
        httpSender.setRequestBody(entity);
        httpSender.setConnectTimeOut(20);
        httpSender.setReadTimeOut(20);
    }

    public static void addRequestHeaderText(HttpSender httpSender, HttpEntity entity) {
        httpSender.addHeader("Content-Type", "application/text");
        httpSender.addHeader("charset", "utf-8");
        httpSender.setRequestBody(entity);
        httpSender.setConnectTimeOut(20);
        httpSender.setReadTimeOut(20);
    }

    public static void addRequestHeaderUrlencoded(HttpSender httpSender) {
        httpSender.addHeader("Content-Type", "application/x-www-form-urlencoded");
        httpSender.addHeader("charset", "utf-8");
        httpSender.setConnectTimeOut(20);
        httpSender.setReadTimeOut(20);
    }

    public static void addRequestHeaderHxzc(HttpSender httpSender, HttpEntity entity) {
        httpSender.addHeader("Content-Type", "application/json;charset=utf-8");
        httpSender.addHeader("charset", "utf-8");
        httpSender.setRequestBody(entity);
        httpSender.setConnectTimeOut(20);
        httpSender.setReadTimeOut(20);
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
