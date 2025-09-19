package com.hanweb.yyjr.zhangye.dao.hc;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.healthcarecommission.RedCrossInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 红十字会信息 Dao
 * @author xueyize
 * @since 2022-02-22
 */
@Repository
public class RedCrossInfoDao extends BaseJdbcDao<Integer, RedCrossInfo> {

    public List<Map<String, Object>> findAllData(){
        String sql="SELECT * FROM hc_redcrossinfo ";
        Query query = createQuery(sql);
        return this.queryForList(query);
    }
}
