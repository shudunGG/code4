package com.hanweb.yyjr.zhangye.dao.hc;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.healthcarecommission.YiQingTele;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 疫情防控联系电话 Dao
 * @author xueyize
 * @since 2022-02-22
 */
@Repository
public class YiQingTeleDao extends BaseJdbcDao<Integer, YiQingTele> {

    public List<Map<String, Object>> findAllData(){
        String sql="SELECT * FROM hc_yiqingtele ";
        Query query = createQuery(sql);
        return this.queryForList(query);
    }
}
