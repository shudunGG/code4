package com.hanweb.yyjr.zhangye.service.ndrcservice;

import com.hanweb.yyjr.zhangye.dao.ndrc.NdrcXingzhengxukeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 发改委行政许可基本信息 Service
 * @author xueyize
 * @since 2022-02-15
 */
@Service
public class NdrcXingzhengxukeService {

    @Autowired
    NdrcXingzhengxukeDao ndrcXingzhengxukeDao;

    public List<Map<String, Object>> findxzxkjdwsmcList(String fdr,String fdrsfz) {
        List<Map<String, Object>> dataMap = ndrcXingzhengxukeDao.findxzxkjdwsmcList(fdr,fdrsfz);
        return dataMap;
    }

    public List<Map<String, Object>> findAllData(String xzxkjdwsmc) {
        List<Map<String, Object>> dataMap = ndrcXingzhengxukeDao.findAllData(xzxkjdwsmc);
        return dataMap;
    }
}
