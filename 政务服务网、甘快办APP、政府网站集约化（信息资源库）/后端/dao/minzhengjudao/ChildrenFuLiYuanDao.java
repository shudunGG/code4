package com.hanweb.yyjr.zhangye.dao.minzhengjudao;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.minzhengju.ChildrenFuLiYuan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 儿童福利院 DAO
 * @author xueyize
 * @since 2022-03-15
 */
@Repository
public class ChildrenFuLiYuanDao extends BaseJdbcDao<Integer, ChildrenFuLiYuan> {

    public List<Map<String, Object>> findAllData(){
        String sql="SELECT * FROM zy_childrenfuliyuan ";
        Query query = createQuery(sql);
        return this.queryForList(query);
    }
}
