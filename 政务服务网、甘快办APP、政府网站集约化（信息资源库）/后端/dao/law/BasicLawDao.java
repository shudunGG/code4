package com.hanweb.yyjr.zhangye.dao.law;


import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.law.BasicLaw;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 基层法律服务所信息 DAO
 * @author xueyize
 * @since 2022-02-24
 */
@Repository
public class BasicLawDao extends BaseJdbcDao<Integer, BasicLaw> {

    public List<Map<String, Object>> findAllData(){
        String sql="SELECT * FROM basiclaw ";
        Query query = createQuery(sql);
        return this.queryForList(query);
    }
}
