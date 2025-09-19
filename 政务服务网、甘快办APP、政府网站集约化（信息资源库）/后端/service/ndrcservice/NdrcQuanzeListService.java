package com.hanweb.yyjr.zhangye.service.ndrcservice;

import com.hanweb.yyjr.zhangye.dao.ndrc.NdrcQuanzeListDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 发改委权责清单 Service
 * @author xueyize
 * @since 2022-02-15
 */
@Service
public class NdrcQuanzeListService {

    @Autowired
    NdrcQuanzeListDao ndrcQuanzeListDao;

    public List<Map<String, Object>> findEventChildNameList(String eventChildName) {
        List<Map<String, Object>> dataMap = ndrcQuanzeListDao.findEventChildNameList(eventChildName);
        return dataMap;
    }

    public Map<String, Object> findAllData(String eventChildName) {
        Map<String, Object> dataMap = ndrcQuanzeListDao.findAllData(eventChildName);
        return dataMap;
    }
}
