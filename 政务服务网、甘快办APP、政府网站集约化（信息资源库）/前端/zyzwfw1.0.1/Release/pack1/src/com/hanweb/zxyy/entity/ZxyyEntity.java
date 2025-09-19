package com.hanweb.zxyy.entity;

import java.io.Serializable;
import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Table;
import com.hanweb.complat.constant.Tables;

/**
 * 在线预约实体，储存预约信息用
 * @author linzhibo
 *
 */
@Table(name = Tables.VS_NETORDER)
public class ZxyyEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8264259585814620106L;

	//预约人
	@Column(name="NAME",type = ColumnType.VARCHAR)
	private String name;
	
	//手机号码
	@Column(name="LINKPHPNE",type = ColumnType.VARCHAR)
	private String linkphpne;

	
	//所属机构
	@Column(name="DEPTCODE",type = ColumnType.VARCHAR)
	private String deptcode;
	
	//预约时间
	@Column(name="ORDERDATETIME",type = ColumnType.DATE)
	private Date orderdatetime;
	
	//申请时间
	@Column(name="ADDDATETIME",type = ColumnType.DATE)
	private Date adddatetime;

	//回复人
	@Column(name="RECEIVER",type = ColumnType.VARCHAR)
	private String receiver;
	
	//回复意见
	@Column(name="REFEEDBACK",type = ColumnType.VARCHAR)
	private String refeedback;
	
	//回复时间
	@Column(name="RETIME",type = ColumnType.DATE)
	private Date retime;
	
	//交换标识
	@Column(name="ONLYMARK",type = ColumnType.VARCHAR)
	private String onlymark;
	
	//身份证号码,法人为社会统一信用代码
	@Column(name="CERTID",type = ColumnType.VARCHAR)
	private String certid;
	
	//预约事项编码
	@Column(name="SERVCODE",type = ColumnType.VARCHAR)
	private String servcode;
	
	//用户名
	@Column(name="USERNAME",type = ColumnType.VARCHAR)
	private String username;
	
	//待定
	@Column(name="UUID",type = ColumnType.VARCHAR)
	private String uuid;
	
/*	//待定
	@Column(name="FORM_INSTANCEID",type = ColumnType.VARCHAR)
	private String form_instanceid;*/
	
	
	//预约来源
	@Column(name="ORDERSOURCE",type = ColumnType.VARCHAR)
	private String ordersource;
	
	//预约内容
	@Column(name="ORDERCONTEXT",type = ColumnType.VARCHAR)
	private String ordercontext;
	
	//时间段
	@Column(name="AMORPM",type = ColumnType.VARCHAR)
	private String amorpm;
	
	//状态
	@Column(name="STATUS",type = ColumnType.VARCHAR)
	private String status;
	
	//事项UUID
	@Column(name="SERVID",type = ColumnType.VARCHAR)
	private String servid;
	
	//预约时间段
	@Column(name="APPOINTMENTTIME",type = ColumnType.VARCHAR)
	private String appointmenttime;
	
	//次数
	private String count;


	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLinkphpne() {
		return linkphpne;
	}

	public void setLinkphpne(String linkphpne) {
		this.linkphpne = linkphpne;
	}

	public String getDeptcode() {
		return deptcode;
	}

	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}

	public Date getOrderdatetime() {
		return orderdatetime;
	}

	public void setOrderdatetime(Date orderdatetime) {
		this.orderdatetime = orderdatetime;
	}

	public Date getAdddatetime() {
		return adddatetime;
	}

	public void setAdddatetime(Date adddatetime) {
		this.adddatetime = adddatetime;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getRefeedback() {
		return refeedback;
	}

	public void setRefeedback(String refeedback) {
		this.refeedback = refeedback;
	}

	public Date getRetime() {
		return retime;
	}

	public void setRetime(Date retime) {
		this.retime = retime;
	}

	public String getOnlymark() {
		return onlymark;
	}

	public void setOnlymark(String onlymark) {
		this.onlymark = onlymark;
	}

	public String getCertid() {
		return certid;
	}

	public void setCertid(String certid) {
		this.certid = certid;
	}

	public String getServcode() {
		return servcode;
	}

	public void setServcode(String servcode) {
		this.servcode = servcode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	

	public String getOrdersource() {
		return ordersource;
	}

	public void setOrdersource(String ordersource) {
		this.ordersource = ordersource;
	}

	public String getOrdercontext() {
		return ordercontext;
	}

	public void setOrdercontext(String ordercontext) {
		this.ordercontext = ordercontext;
	}

	public String getAmorpm() {
		return amorpm;
	}

	public void setAmorpm(String amorpm) {
		this.amorpm = amorpm;
	}

	public String getServid() {
		return servid;
	}

	public void setServid(String servid) {
		this.servid = servid;
	}

	public String getAppointmenttime() {
		return appointmenttime;
	}

	public void setAppointmenttime(String appointmenttime) {
		this.appointmenttime = appointmenttime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
