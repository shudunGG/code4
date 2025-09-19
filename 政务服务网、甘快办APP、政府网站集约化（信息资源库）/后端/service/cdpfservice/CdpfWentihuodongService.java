package com.hanweb.yyjr.zhangye.service.cdpfservice;

import com.hanweb.yyjr.zhangye.dao.cdpf.CdpfWentihuodongDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 残疾人文体娱乐场所基本信息 Service
 * @author xueyize
 * @since 2022-02-15
 */
@Service
public class CdpfWentihuodongService {

    @Autowired
    CdpfWentihuodongDao cdpfWentihuodongDao;

    public List<Map<String, Object>> findAllData(String groupName){
        List<Map<String, Object>> dataMap = cdpfWentihuodongDao.findAllData(groupName);
        return dataMap;
    }
}
