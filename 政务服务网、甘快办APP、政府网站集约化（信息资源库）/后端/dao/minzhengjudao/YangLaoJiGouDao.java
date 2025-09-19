package com.hanweb.yyjr.zhangye.dao.minzhengjudao;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.minzhengju.YangLaoJiGou;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 养老机构信息 DAO
 * @author xueyize
 * @since 2022-03-15
 */
@Repository
public class YangLaoJiGouDao extends BaseJdbcDao<Integer, YangLaoJiGou> {

    public List<Map<String, Object>> findAllData(){
        String sql="SELECT * FROM zy_yanglaojigou ";
        Query query = createQuery(sql);
        return this.queryForList(query);
    }
}
