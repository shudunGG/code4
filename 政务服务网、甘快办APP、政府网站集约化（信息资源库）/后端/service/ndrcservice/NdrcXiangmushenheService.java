package com.hanweb.yyjr.zhangye.service.ndrcservice;

import com.hanweb.yyjr.zhangye.dao.ndrc.NdrcXiangmushenheDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 发改委项目审核基本信息 Service
 * @author xueyize
 * @since 2022-02-15
 */
@Service
public class NdrcXiangmushenheService {

    @Autowired
    NdrcXiangmushenheDao ndrcXiangmushenheDao;

    public List<Map<String, Object>> findAllData(String xmmc) {
        List<Map<String, Object>> dataMap = ndrcXiangmushenheDao.findAllData(xmmc);
        return dataMap;
    }
}
