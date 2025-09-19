package com.hanweb.yyjr.zhangye.entity.minzhengju;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.gsszf.constant.Tables;
import io.swagger.annotations.ApiModelProperty;
import net.sf.oval.constraint.NotNull;

import java.math.BigInteger;

/**
 * 民办社会工作服务机构统计表 实体类
 * @author xueyize
 * @since 2022-03-25
 */
@Table(name= Tables.ZY_MinBanSheHui,comment = "民办社会工作服务机构统计表")
public class MinBanSheHui {

    @ApiModelProperty(value = "id", required = false)
    @NotNull
    @Column(name = "id", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "groupname", required = false)
    @NotNull
    @Column(name = "groupname", type = ColumnType.VARCHAR, length = 255, comment = "社工机构名称")
    private String groupName;

    @ApiModelProperty(value = "address", required = false)
    @NotNull
    @Column(name = "address", type = ColumnType.VARCHAR, length = 255, comment = "详细地址")
    private String address;

    @ApiModelProperty(value = "tele", required = false)
    @NotNull
    @Column(name = "tele", type = ColumnType.VARCHAR, length = 255, comment = "联系电话")
    private String tele;

    @ApiModelProperty(value = "rennum", required = false)
    @NotNull
    @Column(name = "rennum", type = ColumnType.VARCHAR, length = 255, comment = "人数")
    private String renNum;

    @ApiModelProperty(value = "area", required = false)
    @NotNull
    @Column(name = "area", type = ColumnType.VARCHAR, length = 255, comment = "地区 1：市级、2：甘州区、3：临泽县、" +
            "4：山丹县、5：民乐县、6：高台")
    private String area;

    @ApiModelProperty(value = "primarynum", required = false)
    @NotNull
    @Column(name = "primarynum", type = ColumnType.VARCHAR, length = 255, comment = "初级人数")
    private String primaryNum;

    @ApiModelProperty(value = "middlenum", required = false)
    @NotNull
    @Column(name = "middlenum", type = ColumnType.VARCHAR, length = 255, comment = "中级人数")
    private String middleNum;

    @ApiModelProperty(value = "partynum", required = false)
    @NotNull
    @Column(name = "partynum", type = ColumnType.VARCHAR, length = 255, comment = "党员数目")
    private String partyNum;

    @ApiModelProperty(value = "psychologicalnum", required = false)
    @NotNull
    @Column(name = "psychologicalnum", type = ColumnType.VARCHAR, length = 255, comment = "心理咨询师人数")
    private String psychologicalNum;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getRenNum() {
        return renNum;
    }

    public void setRenNum(String renNum) {
        this.renNum = renNum;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPrimaryNum() {
        return primaryNum;
    }

    public void setPrimaryNum(String primaryNum) {
        this.primaryNum = primaryNum;
    }

    public String getMiddleNum() {
        return middleNum;
    }

    public void setMiddleNum(String middleNum) {
        this.middleNum = middleNum;
    }

    public String getPartyNum() {
        return partyNum;
    }

    public void setPartyNum(String partyNum) {
        this.partyNum = partyNum;
    }

    public String getPsychologicalNum() {
        return psychologicalNum;
    }

    public void setPsychologicalNum(String psychologicalNum) {
        this.psychologicalNum = psychologicalNum;
    }
}
