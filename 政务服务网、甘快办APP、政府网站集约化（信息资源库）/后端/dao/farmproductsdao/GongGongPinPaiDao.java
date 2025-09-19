package com.hanweb.yyjr.zhangye.dao.farmproductsdao;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.farmproducts.GongGongPinPai;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 公共品牌信息 DAO
 * @author xueyize
 * @since 2022-02-15
 */
@Repository
public class GongGongPinPaiDao extends BaseJdbcDao<Integer, GongGongPinPai> {

    public List<Map<String, Object>> findAllData(){
        String sql="SELECT * FROM ZY_gonggongpinpai ";
        Query query = createQuery(sql);
        return this.queryForList(query);
    }
}
