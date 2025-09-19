package com.hanweb.yyjr.zhangye.service.impl;

import com.hanweb.common.util.HttpSender;
import com.hanweb.common.util.log.Log;
import com.hanweb.common.util.log.LogFactory;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.yyjr.zhangye.service.ZyStaticDataService;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @program: yyjrzzxt
 * @ClassName: ZyStaticDataServiceImpl
 * @description: 张掖三十个静态数据（共享交换）
 * @author: jq
 * @create: 2024-11-21 22:17
 */
@Service
public class ZyStaticDataServiceImpl implements ZyStaticDataService {

    private static final Log LOGGER = LogFactory.getInstance(ZyStaticDataServiceImpl.class);


    @Value("${project.zyStaticData.url:http://172.12.0.253:8081/tjbgxx/share/3/gkbyyfbjk2}")
    private String url;

    @Override
    public String getZyStaticData(String params) {
        try (HttpSender httpSender = HttpSender.getInstancePost(url)) {
            httpSender.addHeader("client_key", "1731373215");

            StringEntity postingString = new StringEntity(params, "UTF-8");
            httpSender.setRequestBody(postingString);
            if (httpSender.execute()) {
                String req = httpSender.getResult();
                LOGGER.debug("三方接口获取成功：" + req);
                return req;
            } else {
                LOGGER.debug("获取数据失败！<CODE>" + httpSender.getCode() + "-->" + getRemoteError(httpSender));
                throw new RuntimeException("获取数据失败！<CODE>" + httpSender.getCode() + "-->" + getRemoteError(httpSender));
            }
        } catch (Exception e) {
            LOGGER.error("获取数据失败！", e);
            throw new OperationException("获取数据失败！" + e.getMessage());
        }
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
            LOGGER.error("发生异常", e);
        }
        return sb.toString();
    }
}
