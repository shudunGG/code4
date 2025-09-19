package com.hanweb.yyjr.zhangye.dao.ndrc;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.ndrc.NdrcXingzhengxuke;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 发改委行政许可 Dao
 * @author xueyize
 * @since 2022-02-15
 */
@Repository
public class NdrcXingzhengxukeDao extends BaseJdbcDao<Integer, NdrcXingzhengxuke> {

    public List<Map<String, Object>> findxzxkjdwsmcList(String fdr,String fdrsfz){
        String sql="SELECT xzxkjdwsmc FROM ndrc_xingzhengxuke where fdr = :fdr and fdrsfz = :fdrsfz";
        Query query = createQuery(sql);
        query.addParameter("fdr",fdr);
        query.addParameter("fdrsfz",fdrsfz);
        return this.queryForList(query);
    }

    public List<Map<String, Object>> findAllData(String xzxkjdwsmc){
        String sql="SELECT * FROM ndrc_xingzhengxuke where xzxkjdwsmc = :xzxkjdwsmc";
        Query query = createQuery(sql);
        query.addParameter("xzxkjdwsmc",xzxkjdwsmc);
        return this.queryForList(query);
    }
}
