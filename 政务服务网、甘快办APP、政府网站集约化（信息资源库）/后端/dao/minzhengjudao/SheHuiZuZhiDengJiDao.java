package com.hanweb.yyjr.zhangye.dao.minzhengjudao;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.minzhengju.SheHuiZuZhiDengJi;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 社会组织登记服务 DAO
 * @author xueyize
 * @since 2022-03-15
 */
@Repository
public class SheHuiZuZhiDengJiDao extends BaseJdbcDao<Integer, SheHuiZuZhiDengJi> {

    public List<Map<String, Object>> findAllData(){
        String sql="SELECT * FROM zy_shehuizuzhidengji ";
        Query query = createQuery(sql);
        return this.queryForList(query);
    }
}
