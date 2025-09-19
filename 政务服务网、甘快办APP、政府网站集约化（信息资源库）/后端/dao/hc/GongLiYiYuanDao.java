package com.hanweb.yyjr.zhangye.dao.hc;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.healthcarecommission.GongLiYiYuan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 公立医院 Dao
 * @author xueyize
 * @since 2022-03-16
 */
@Repository
public class GongLiYiYuanDao extends BaseJdbcDao<Integer, GongLiYiYuan> {

    public List<Map<String, Object>> findAllData(){
        String sql="SELECT * FROM zy_gongliyiyuan ";
        Query query = createQuery(sql);
        return this.queryForList(query);
    }
}
