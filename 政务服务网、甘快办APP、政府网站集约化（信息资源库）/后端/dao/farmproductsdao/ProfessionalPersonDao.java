package com.hanweb.yyjr.zhangye.dao.farmproductsdao;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.farmproducts.ProfessionalPerson;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 农业职称专技人员 DAO
 * @author xueyize
 * @since 2022-02-15
 */
@Repository
public class ProfessionalPersonDao extends BaseJdbcDao<Integer, ProfessionalPerson> {

    public List<Map<String, Object>> findAllData(){
        String sql="SELECT * FROM zy_professionalperson ";
        Query query = createQuery(sql);
        return this.queryForList(query);
    }
}
