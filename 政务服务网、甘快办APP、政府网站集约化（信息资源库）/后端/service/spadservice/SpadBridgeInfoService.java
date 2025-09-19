package com.hanweb.yyjr.zhangye.service.spadservice;

import com.hanweb.yyjr.zhangye.dao.spad.SpadBridgeInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 桥梁信息 Service
 * @author xueyize
 * @since 2022-02-15
 */
@Service
public class SpadBridgeInfoService {

    @Autowired
    SpadBridgeInfoDao spadBridgeInfoDao;

    public List<Map<String, Object>> findBridgeNameList(String bridgename) {
        List<Map<String, Object>> dataMap = spadBridgeInfoDao.findBridgeNameList(bridgename);
        return dataMap;
    }

    public Map<String, Object> findAllData(String bridgename) {
        Map<String, Object> dataMap = spadBridgeInfoDao.findAllData(bridgename);
        return dataMap;
    }
}
