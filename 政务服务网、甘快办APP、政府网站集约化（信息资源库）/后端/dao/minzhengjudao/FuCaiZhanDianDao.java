package com.hanweb.yyjr.zhangye.dao.minzhengjudao;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.minzhengju.FuCaiZhanDian;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 福彩站点 DAO
 * @author xueyize
 * @since 2022-03-15
 */
@Repository
public class FuCaiZhanDianDao extends BaseJdbcDao<Integer, FuCaiZhanDian> {

    public List<Map<String, Object>> findAllData(){
        String sql="SELECT * FROM zy_fucaizhandian ";
        Query query = createQuery(sql);
        return this.queryForList(query);
    }
}
