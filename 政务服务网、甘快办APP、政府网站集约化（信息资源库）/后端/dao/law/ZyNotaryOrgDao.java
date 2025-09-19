package com.hanweb.yyjr.zhangye.dao.law;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.law.ZyNotaryOrg;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 公证机构信息 DAO
 * @author xueyize
 * @since 2022-02-24
 */
@Repository
public class ZyNotaryOrgDao extends BaseJdbcDao<Integer, ZyNotaryOrg> {

    public List<Map<String, Object>> findAllData(){
        String sql="SELECT * FROM zy_notaryorg ";
        Query query = createQuery(sql);
        return this.queryForList(query);
    }
}
