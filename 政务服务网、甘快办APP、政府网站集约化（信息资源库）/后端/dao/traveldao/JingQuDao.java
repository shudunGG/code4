package com.hanweb.yyjr.zhangye.dao.traveldao;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.travel.JingQu;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 景区 DAO
 * @author xueyize
 * @since 2022-03-07
 */
@Repository
public class JingQuDao extends BaseJdbcDao<Integer, JingQu> {

    public List<Map<String, Object>> findAllData(){
        String sql="SELECT * FROM zy_jingqu ";
        Query query = createQuery(sql);
        return this.queryForList(query);
    }
}
