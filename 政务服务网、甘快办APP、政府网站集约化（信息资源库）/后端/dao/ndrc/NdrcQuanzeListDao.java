package com.hanweb.yyjr.zhangye.dao.ndrc;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.LikeType;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.ndrc.NdrcQuanzeList;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 发改委权责清单 Dao
 * @author xueyize
 * @since 2022-02-15
 */
@Repository
public class NdrcQuanzeListDao extends BaseJdbcDao<Integer, NdrcQuanzeList> {

    public List<Map<String, Object>> findEventChildNameList(String eventChildName){
        String sql="SELECT EventChildName FROM ndrc_quanzeList where EventChildName like :EventChildName";
        Query query = createQuery(sql);
        query.addParameter("EventChildName",eventChildName, LikeType.LR);
        return this.queryForList(query);
    }

    public Map<String, Object> findAllData(String eventChildName){
        String sql="SELECT * FROM ndrc_quanzeList where EventChildName = :EventChildName";
        Query query = createQuery(sql);
        query.addParameter("EventChildName",eventChildName);
        return this.queryForMap(query);
    }
}
