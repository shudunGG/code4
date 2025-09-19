package com.hanweb.yyjr.zhangye.dao.rensheju;

import com.hanweb.common.basedao.BaseJdbcDao;
import com.hanweb.common.basedao.LikeType;
import com.hanweb.common.basedao.Query;
import com.hanweb.yyjr.zhangye.entity.rensheju.GongZiBaoChou;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 人社局工资报酬 Dao
 * @author xueyize
 * @since 2022-02-22
 */
@Repository
public class RenSheJuGongZiBaoBiaoDao extends BaseJdbcDao<Integer, GongZiBaoChou> {

    public List<Map<String, Object>> findAllData(String positionName){
        String sql="SELECT * FROM rensheju_gongzibaochou where  PositionName like :positionName";
        Query query = createQuery(sql);
        query.addParameter("positionName",positionName, LikeType.LR);
        return this.queryForList(query);
    }
}
