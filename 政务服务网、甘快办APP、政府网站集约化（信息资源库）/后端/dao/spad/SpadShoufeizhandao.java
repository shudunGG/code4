package com.hanweb.yyjr.zhangye.dao.spad;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.spad.SpadShouFeiZhan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 交委收费站 Dao
 * @author xueyize
 * @since 2022-02-15
 */
@Repository
public class SpadShoufeizhandao extends BaseJdbcDao<Integer, SpadShouFeiZhan> {

    public List<Map<String, Object>> findAllData(){
        String sql="SELECT * FROM spad_shoufeizhan ";
        Query query = createQuery(sql);
        return this.queryForList(query);
    }
}
