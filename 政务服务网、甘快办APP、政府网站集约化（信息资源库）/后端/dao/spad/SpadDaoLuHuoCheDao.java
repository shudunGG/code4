package com.hanweb.yyjr.zhangye.dao.spad;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.LikeType;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.spad.SpadDaoLuHuoChe;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 道路货车 Dao
 * @author xueyize
 * @since 2022-02-15
 */
@Repository
public class SpadDaoLuHuoCheDao extends BaseJdbcDao<Integer, SpadDaoLuHuoChe> {

    public List<Map<String, Object>> findAllData(String jczmc){
        String sql="SELECT * FROM spad_daoluhuoche where jczmc like :jczmc";
        Query query = createQuery(sql);
        query.addParameter("jczmc",jczmc, LikeType.LR);
        return this.queryForList(query);
    }
}
