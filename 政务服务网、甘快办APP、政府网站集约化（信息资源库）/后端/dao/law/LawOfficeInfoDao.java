package com.hanweb.yyjr.zhangye.dao.law;


import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.law.LawOfficeInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 律师事务所信息 DAO
 * @author xueyize
 * @since 2022-02-24
 */
@Repository
public class LawOfficeInfoDao extends BaseJdbcDao<Integer, LawOfficeInfo> {

    public List<Map<String, Object>> findAllData(){
        String sql="SELECT * FROM lawofficeinfo ";
        Query query = createQuery(sql);
        return this.queryForList(query);
    }
}
