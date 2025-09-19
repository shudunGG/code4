package com.hanweb.yyjr.zhangye.service.spadservice;

import com.hanweb.yyjr.zhangye.dao.spad.SpadShoufeizhandao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 交委收费站 Service
 * @author xueyize
 * @since 2022-02-15
 */
@Service
public class SpadShouFeiZhanService {

    @Autowired
    SpadShoufeizhandao spadShoufeizhanDao;

    public List<Map<String, Object>> findAllData(){
        List<Map<String, Object>> dataMap = spadShoufeizhanDao.findAllData();
        return dataMap;
    }
}
