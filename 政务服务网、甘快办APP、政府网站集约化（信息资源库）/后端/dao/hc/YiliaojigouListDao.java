package com.hanweb.yyjr.zhangye.dao.hc;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.healthcarecommission.YiliaojigouList;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 医疗机构信息 Dao
 * @author xueyize
 * @since 2022-02-22
 */
@Repository
public class YiliaojigouListDao extends BaseJdbcDao<Integer, YiliaojigouList> {

    public List<Map<String, Object>> findAllData(String type){
        String sql="SELECT * FROM hc_yiliaojigoulist where type = :type";
        Query query = createQuery(sql);
        query.addParameter("type",type);
        return this.queryForList(query);
    }

    public List<Map<String, Object>> findAllTypeList(){
        String sql="SELECT DISTINCT type FROM hc_yiliaojigoulist ;";
        Query query = createQuery(sql);
        return this.queryForList(query);
    }
}
