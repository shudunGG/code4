package com.hanweb.yyjr.zhangye.dao.ndrc;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.ndrc.NdrcXiangmushenhe;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 发改委项目审核 Dao
 * @author xueyize
 * @since 2022-02-15
 */
@Repository
public class NdrcXiangmushenheDao extends BaseJdbcDao<Integer, NdrcXiangmushenhe> {

    public List<Map<String, Object>> findAllData(String xmmc){
        String sql="SELECT * FROM ndrc_xiangmushenhe where xmmc = :xmmc";
        Query query = createQuery(sql);
        query.addParameter("xmmc",xmmc);
        return this.queryForList(query);
    }
}
