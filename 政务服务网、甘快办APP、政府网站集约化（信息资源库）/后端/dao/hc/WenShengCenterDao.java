package com.hanweb.yyjr.zhangye.dao.hc;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.LikeType;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.healthcarecommission.WenShengCenter;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 卫生中心数据 Dao
 * @author xueyize
 * @since 2022-02-22
 */
@Repository
public class WenShengCenterDao extends BaseJdbcDao<Integer, WenShengCenter> {

    public List<Map<String, Object>> findWeiShengNameList(String name){
        String sql="SELECT name FROM hc_wenshengcenter where name like :name";
        Query query = createQuery(sql);
        query.addParameter("name",name, LikeType.LR);
        return this.queryForList(query);
    }

    public Map<String, Object> findAllData(String name){
        String sql="SELECT * FROM hc_wenshengcenter where name = :name";
        Query query = createQuery(sql);
        query.addParameter("name",name);
        return this.queryForMap(query);
    }
}
