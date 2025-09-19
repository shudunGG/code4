package com.hanweb.yyjr.zhangye.dao.traveldao;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.travel.StarLevelNongJiaLe;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 星级农家乐 DAO
 * @author xueyize
 * @since 2022-02-15
 */
@Repository
public class StarLevelNongJiaLeDao extends BaseJdbcDao<Integer, StarLevelNongJiaLe> {

    public List<Map<String, Object>> findAllData(){
        String sql="SELECT * FROM zy_starlevelnongjiale ";
        Query query = createQuery(sql);
        return this.queryForList(query);
    }
}
