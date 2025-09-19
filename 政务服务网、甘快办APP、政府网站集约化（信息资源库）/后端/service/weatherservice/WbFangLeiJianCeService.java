package com.hanweb.yyjr.zhangye.service.weatherservice;

import com.hanweb.yyjr.zhangye.dao.weather.WbFangLeiJiancCeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 气象局防雷检测 Service
 * @author xueyize
 * @since 2022-02-15
 */
@Service
public class WbFangLeiJianCeService {

    @Autowired
    WbFangLeiJiancCeDao wbFangLeiJiancCeDao;

    public List<Map<String, Object>> findAllData(String xmmc) {
        List<Map<String, Object>> dataMap = wbFangLeiJiancCeDao.findAllData(xmmc);
        return dataMap;
    }
}
