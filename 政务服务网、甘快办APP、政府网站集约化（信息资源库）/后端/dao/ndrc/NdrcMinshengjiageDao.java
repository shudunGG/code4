package com.hanweb.yyjr.zhangye.dao.ndrc;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.LikeType;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.ndrc.NdrcMinshengjiage;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 发改委民生价格 Dao
 * @author xueyize
 * @since 2022-02-22
 */
@Repository
public class NdrcMinshengjiageDao extends BaseJdbcDao<Integer, NdrcMinshengjiage> {

    public List<Map<String, Object>> findGoodsContentList(String goodsContent){
        String sql="SELECT GoodsContent FROM ndrc_minshengjiage where GoodsContent like :goodsContent";
        Query query = createQuery(sql);
        query.addParameter("goodsContent",goodsContent, LikeType.LR);
        return this.queryForList(query);
    }

    public Map<String, Object> findAllData(String goodsContent){
        String sql="SELECT * FROM ndrc_minshengjiage where GoodsContent = :goodsContent";
        Query query = createQuery(sql);
        query.addParameter("goodsContent",goodsContent);
        return this.queryForMap(query);
    }
}
