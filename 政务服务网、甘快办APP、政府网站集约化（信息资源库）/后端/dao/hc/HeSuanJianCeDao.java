package com.hanweb.yyjr.zhangye.dao.hc;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.healthcarecommission.HeSuanJianCe;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 核酸检测 Dao
 * @author xueyize
 * @since 2022-03-18
 */
@Repository
public class HeSuanJianCeDao extends BaseJdbcDao<Integer, HeSuanJianCe> {

    public List<Map<String, Object>> findAllData(){
        String sql="SELECT * FROM zy_hesuanjiance ";
        Query query = createQuery(sql);
        return this.queryForList(query);
    }
}
