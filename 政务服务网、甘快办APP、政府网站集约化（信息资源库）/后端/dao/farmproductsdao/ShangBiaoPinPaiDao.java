package com.hanweb.yyjr.zhangye.dao.farmproductsdao;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.farmproducts.ShangBiaoPinPai;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 商标品牌信息 DAO
 * @author xueyize
 * @since 2022-02-15
 */
@Repository
public class ShangBiaoPinPaiDao extends BaseJdbcDao<Integer, ShangBiaoPinPai> {

    public List<Map<String, Object>> findAllData(){
        String sql="SELECT * FROM zy_shangbiaopinpai ";
        Query query = createQuery(sql);
        return this.queryForList(query);
    }
}
