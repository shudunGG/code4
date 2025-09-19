package com.hanweb.yyjr.zhangye.dao.cdpf;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.cdpf.CdpfLianhehui;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 残疾人联合会 Dao
 * @author xueyize
 * @since 2022-02-15
 */
@Repository
public class CdpfLianhehuiDao extends BaseJdbcDao<Integer, CdpfLianhehui> {

    public List<Map<String, Object>> findAllData(){
        String sql="SELECT * FROM cdpf_lianhehui ";
        Query query = createQuery(sql);
        return this.queryForList(query);
    }
}
