package com.hanweb.yyjr.zhangye.dao.law;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.law.LegalAid;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 法律援助 DAO
 * @author xueyize
 * @since 2022-02-24
 */
@Repository
public class LegalAidDao extends BaseJdbcDao<Integer, LegalAid> {

    public List<Map<String, Object>> findAllData(){
        String sql="SELECT * FROM zy_legalaid ";
        Query query = createQuery(sql);
        return this.queryForList(query);
    }
}
