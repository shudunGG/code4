package com.hanweb.yyjr.zhangye.dao.traveldao;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.travel.StarLevelRestaurant;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 星级饭店 DAO
 * @author xueyize
 * @since 2022-02-15
 */
@Repository
public class StarLevelRestaurantDao extends BaseJdbcDao<Integer, StarLevelRestaurant> {

    public List<Map<String, Object>> findAllData(){
        String sql="SELECT * FROM zy_starlevelrestaurant ";
        Query query = createQuery(sql);
        return this.queryForList(query);
    }
}
