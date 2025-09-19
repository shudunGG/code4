package com.hanweb.yyjr.zhangye.dao.hc;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.healthcarecommission.BaoJianJiGou;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 保健机构信息 Dao
 * @author xueyize
 * @since 2022-02-22
 */
@Repository
public class BaoJianJiGouDao extends BaseJdbcDao<Integer, BaoJianJiGou> {

    public List<Map<String, Object>> findAllData(){
        String sql="SELECT * FROM hc_baojianjigou ";
        Query query = createQuery(sql);
        return this.queryForList(query);
    }
}
