package com.hanweb.yyjr.zhangye.dao.weather;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.LikeType;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.weatherbureau.WbFangLeiJianCe;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 气象局防雷检测 Dao
 * @author xueyize
 * @since 2022-02-15
 */
@Repository
public class WbFangLeiJiancCeDao extends BaseJdbcDao<Integer, WbFangLeiJianCe> {

    public List<Map<String, Object>> findAllData(String dwmc){
        String sql="SELECT * FROM wb_fangleijiance where dwmc like :dwmc";
        Query query = createQuery(sql);
        query.addParameter("dwmc",dwmc, LikeType.LR);
        return this.queryForList(query);
    }
}
