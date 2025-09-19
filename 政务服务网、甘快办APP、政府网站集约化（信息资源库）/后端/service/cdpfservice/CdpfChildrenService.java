package com.hanweb.yyjr.zhangye.service.cdpfservice;

import com.hanweb.yyjr.zhangye.dao.cdpf.CdpfChildrenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 残疾儿童康复基本信息 Service
 * @author xueyize
 * @since 2022-02-15
 */
@Service
public class CdpfChildrenService {

    @Autowired
    CdpfChildrenDao cdpfChildrenDao;

    public List<Map<String, Object>> findAllData(String groupName){
        List<Map<String, Object>> dataMap = cdpfChildrenDao.findAllData(groupName);
        return dataMap;
    }
}
