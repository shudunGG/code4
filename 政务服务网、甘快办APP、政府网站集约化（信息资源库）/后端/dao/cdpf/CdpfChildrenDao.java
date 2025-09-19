package com.hanweb.yyjr.zhangye.dao.cdpf;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.LikeType;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.cdpf.CdpfChildren;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 残疾儿童康复基本信息 DAO
 * @author xueyize
 * @since 2022-02-15
 */
@Repository
public class CdpfChildrenDao extends BaseJdbcDao<Integer, CdpfChildren> {

    public List<Map<String, Object>> findAllData(String groupName){
        String sql="SELECT * FROM cdpf_children where GroupName like :GroupName";
        Query query = createQuery(sql);
        query.addParameter("GroupName",groupName, LikeType.LR);
        return this.queryForList(query);
    }
}
