package com.hanweb.yyjr.zhangye.service.cdpfservice;

import com.hanweb.yyjr.zhangye.dao.cdpf.CdpfLianhehuiDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 残疾人联合会基本信息 Service
 * @author xueyize
 * @since 2022-02-15
 */
@Service
public class CdpfLianhehuiService {

    @Autowired
    CdpfLianhehuiDao cdpfLianhehuiDao;

    public List<Map<String, Object>> findAllData(){
        List<Map<String, Object>> dataMap = cdpfLianhehuiDao.findAllData();
        return dataMap;
    }
}
