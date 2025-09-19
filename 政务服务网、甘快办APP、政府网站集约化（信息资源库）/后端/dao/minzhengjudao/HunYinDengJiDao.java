package com.hanweb.yyjr.zhangye.dao.minzhengjudao;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.minzhengju.HunYinDengJi;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 婚姻登记 DAO
 * @author xueyize
 * @since 2022-03-15
 */
@Repository
public class HunYinDengJiDao extends BaseJdbcDao<Integer, HunYinDengJi> {

    public List<Map<String, Object>> findAllData(){
        String sql="SELECT * FROM zy_hunyindengji ";
        Query query = createQuery(sql);
        return this.queryForList(query);
    }
}
