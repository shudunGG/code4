package com.hanweb.yyjr.zhangye.dao.traveldao;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.travel.Library;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 图书馆 DAO
 * @author xueyize
 * @since 2022-03-07
 */
@Repository
public class LibraryDao extends BaseJdbcDao<Integer, Library> {

    public List<Map<String, Object>> findAllData(){
        String sql="SELECT * FROM zy_library ";
        Query query = createQuery(sql);
        return this.queryForList(query);
    }
}
