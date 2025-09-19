package com.hanweb.yyjr.zhangye.dao.traveldao;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.travel.LvXingShe;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 旅行社 DAO
 * @author xueyize
 * @since 2022-02-15
 */
@Repository
public class LvXingSheDao extends BaseJdbcDao<Integer, LvXingShe> {

    public List<Map<String, Object>> findAllData(){
        String sql="SELECT * FROM zy_lvxingshe ";
        Query query = createQuery(sql);
        return this.queryForList(query);
    }
}
