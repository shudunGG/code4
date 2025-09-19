package com.hanweb.yyjr.zhangye.dao.traveldao;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.travel.WenHuaGuan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 文化馆 DAO
 * @author xueyize
 * @since 2022-02-15
 */
@Repository
public class WenHuaGuanDao extends BaseJdbcDao<Integer, WenHuaGuan> {

    public List<Map<String, Object>> findAllData(){
        String sql="SELECT * FROM zy_wenhuaguan ";
        Query query = createQuery(sql);
        return this.queryForList(query);
    }

}
