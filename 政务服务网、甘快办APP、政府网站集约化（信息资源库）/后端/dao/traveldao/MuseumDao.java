package com.hanweb.yyjr.zhangye.dao.traveldao;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.travel.Museum;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 博物馆 DAO
 * @author xueyize
 * @since 2022-03-07
 */
@Repository
public class MuseumDao extends BaseJdbcDao<Integer, Museum> {

    public List<Map<String, Object>> findAllData(){
        String sql="SELECT * FROM zy_museum ";
        Query query = createQuery(sql);
        return this.queryForList(query);
    }
}
