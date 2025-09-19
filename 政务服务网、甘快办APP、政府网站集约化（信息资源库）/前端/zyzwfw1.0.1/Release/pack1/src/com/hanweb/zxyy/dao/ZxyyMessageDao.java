package com.hanweb.zxyy.dao;

import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.complat.constant.Tables;
import com.hanweb.zxyy.entity.ZxyyEntity;

/**
 * 预约数据表操作
 * @author linzhibo
 *
 */
public class ZxyyMessageDao extends BaseJdbcDAO<Integer, ZxyyEntity> {
	/**
	 * 根据事项主键，查询事项
	 * @param unid
	 * @return
	 */
	public Integer getItem(ZxyyEntity entity) {
		Integer insert = this.insert(entity);
		return insert;
	}
	
	/**
	 * 删除数据库数据
	 * @param field_3651 事项主键
	 * @param loginname 登陆名称
	 * @return
	 */
	public Boolean cancel(String field_3651, String loginname) {
		String sql = "DELETE FROM "+Tables.VS_NETORDER+" WHERE SERVID = :field_3651 AND USERNAME = :loginname AND STATUS = :status";
		Query query = createQuery(sql);
		query.addParameter("field_3651", field_3651);
		query.addParameter("loginname", loginname);
		query.addParameter("status", "未取号");
		boolean delete = this.delete(query);
		return delete;
	}
	/**
	 * 根据事项id和用户登陆名统计未取号的统计预约信息
	 * @param unid
	 * @param loginname
	 * @return
	 */
	public Integer zxyyCount(String unid, String loginname) {
		String sql = "SELECT COUNT(SERVID) FROM "+Tables.VS_NETORDER+" WHERE USERNAME = :loginame AND SERVID = :unid AND STATUS = :status";
		Query query = createQuery(sql);
		query.addParameter("unid", unid);
		query.addParameter("loginame", loginname);
		query.addParameter("status", "未取号");
		Integer integer = this.queryForInteger(query);
		return integer;
	}
	
	
	/**
	 * 根据事项id和未取号的统计预约信息
	 * @param unid
	 * @param loginname
	 * @return
	 */
	public Integer zxyyCountByunid(String unid) {
		String sql = "SELECT COUNT(SERVID) FROM "+Tables.VS_NETORDER+" WHERE SERVID = :unid AND STATUS = :status";
		Query query = createQuery(sql);
		query.addParameter("unid", unid);
		query.addParameter("status", "未取号");
		Integer integer = this.queryForInteger(query);
		return integer;
	}
	/**
	 * 根据时间段查询集合
	 * @param field_3651
	 * @param string
	 */
	public List<ZxyyEntity> zxyyCountDate(String field_3651, String date) {
		String sql = "SELECT ORDERDATETIME,COUNT(ORDERDATETIME) as count FROM "+Tables.VS_NETORDER+" WHERE SERVID = :unid AND STATUS = :status AND APPOINTMENTTIME = :date GROUP BY ORDERDATETIME";
		Query query = createQuery(sql);
		query.addParameter("unid", field_3651);
		query.addParameter("date", date);
		query.addParameter("status", "未取号");
		List<ZxyyEntity> list = this.queryForBeans(query, ZxyyEntity.class);
		return list;
	}
}
