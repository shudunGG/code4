package com.hanweb.zxyy.dao;


import java.util.List;
import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.complat.constant.Tables;
import com.hanweb.zxyy.entity.ItemEntity;
/**
 * 
 * @Description:TODO(在线预约)
 * @author linzhibo
 * @date 2020年4月30日下午5:21:49
 */
public class ZxyyItemDao extends BaseJdbcDAO<Integer, ItemEntity> {
	/**
	 * 根据事项主键，查询事项
	 * @param unid
	 * @return
	 */
	public List<ItemEntity> getItem(String unid) {
		String sql ="SELECT i_id,i_cataid,c_createtime,vc_name,field_3651,field_3665,field_3658,field_3686,field_3723,field_3663 FROM "+Tables.JCMS_59+" WHERE field_3651 = :unid";
		Query query =createQuery(sql);
		query.addParameter("unid", unid);
		List<ItemEntity> list =this.queryForBeans(query, ItemEntity.class);
		return list;
	}
	

}
