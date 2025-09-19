package com.hanweb.yyjr.zhangye.dao.minzhengjudao;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.minzhengju.SheHuiZuZhiXinXi;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 社会组织信息 DAO
 * @author xueyize
 * @since 2022-03-15
 */
@Repository
public class SheHuiZuZhiXinXiDao extends BaseJdbcDao<Integer, SheHuiZuZhiXinXi> {

    public List<Map<String, Object>> findAllData(){
        String sql="SELECT * FROM zy_shehuizuzhixinxi ";
        Query query = createQuery(sql);
        return this.queryForList(query);
    }
}
