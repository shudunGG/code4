package com.hanweb.yyjr.zhangye.dao.law;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.law.JudicialExpertise;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 司法鉴定所 DAO
 * @author xueyize
 * @since 2022-02-24
 */
@Repository
public class JudicialExpertiseDao extends BaseJdbcDao<Integer, JudicialExpertise> {

    public List<Map<String, Object>> findAllData(){
        String sql="SELECT * FROM zy_judicialexpertise ";
        Query query = createQuery(sql);
        return this.queryForList(query);
    }
}
