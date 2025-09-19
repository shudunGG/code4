package com.hanweb.yyjr.zhangye.dao.hc;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.LikeType;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.healthcarecommission.InstitutionsBeds;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 机构床位信息 Dao
 * @author xueyize
 * @since 2022-02-22
 */
@Repository
public class InstitutionsBedsDao extends BaseJdbcDao<Integer, InstitutionsBeds> {

    public List<Map<String, Object>> findDataByArea(String area){
        String sql="SELECT * FROM hc_institutionsbeds where area like :area";
        Query query = createQuery(sql);
        query.addParameter("area",area, LikeType.LR);
        return this.queryForList(query);
    }

//    public List<Map<String, Object>> findAllData(){
//        String sql="SELECT count(synhospnum) as  FROM hc_institutionsbeds ";
//        Query query = createQuery(sql);
//        return this.queryForList(query);
//    }
}
