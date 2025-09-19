package com.hanweb.yyjr.zhangye.dao.minzhengjudao;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.minzhengju.JiuZhuZhan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 救助站 DAO
 * @author xueyize
 * @since 2022-03-15
 */
@Repository
public class JiuZhuZhanDao extends BaseJdbcDao<Integer, JiuZhuZhan> {

    public List<Map<String, Object>> findAllData(){
        String sql="SELECT * FROM zy_jiuzhuzhan ";
        Query query = createQuery(sql);
        return this.queryForList(query);
    }
}
