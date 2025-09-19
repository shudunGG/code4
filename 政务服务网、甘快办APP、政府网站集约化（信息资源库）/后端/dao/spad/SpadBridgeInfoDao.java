package com.hanweb.yyjr.zhangye.dao.spad;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.LikeType;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.spad.SpadBridgeInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 桥梁信息 Dao
 * @author xueyize
 * @since 2022-02-15
 */
@Repository
public class SpadBridgeInfoDao extends BaseJdbcDao<Integer, SpadBridgeInfo> {

    public List<Map<String, Object>> findBridgeNameList(String bridgename){
        String sql="SELECT bridgename FROM spad_bridgeInfo where bridgename like :bridgename";
        Query query = createQuery(sql);
        query.addParameter("bridgename",bridgename, LikeType.LR);
        return this.queryForList(query);
    }

    public Map<String, Object> findAllData(String bridgename){
        String sql="SELECT * FROM spad_bridgeInfo where bridgename = :bridgename";
        Query query = createQuery(sql);
        query.addParameter("bridgename",bridgename);
        return this.queryForMap(query);
    }
}
