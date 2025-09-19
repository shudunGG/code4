package com.hanweb.yyjr.zhangye.service.spadservice;


import com.hanweb.yyjr.zhangye.dao.spad.SpadDaoLuHuoCheDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 道路货车 Service
 * @author xueyize
 * @since 2022-02-15
 */
@Service
public class SpadDaoLuHuoCheService {

    @Autowired
    SpadDaoLuHuoCheDao spadDaoluhuocheDao;

    public List<Map<String, Object>> findAllData(String jczmc) {
        List<Map<String, Object>> dataMap = spadDaoluhuocheDao.findAllData(jczmc);
        return dataMap;
    }
}
