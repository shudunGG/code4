package com.hanweb.yyjr.zhangye.dao.hc;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.LikeType;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.healthcarecommission.YiMiaoJieZhong;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 疫苗接种信息 Dao
 * @author xueyize
 * @since 2022-02-22
 */
@Repository
public class YiMiaoJieZhongDao extends BaseJdbcDao<Integer, YiMiaoJieZhong> {

    public List<Map<String, Object>> findJieZhongList(String jieZhongDianName){
        String sql="SELECT jieZhongDianName FROM hc_yimiaojiezhong where jieZhongDianName like :jieZhongDianName";
        Query query = createQuery(sql);
        query.addParameter("jieZhongDianName",jieZhongDianName, LikeType.LR);
        return this.queryForList(query);
    }

    public Map<String, Object> findAllData(String jieZhongDianName){
        String sql="SELECT * FROM hc_yimiaojiezhong where jieZhongDianName = :jieZhongDianName";
        Query query = createQuery(sql);
        query.addParameter("jieZhongDianName",jieZhongDianName);
        return this.queryForMap(query);
    }
}
