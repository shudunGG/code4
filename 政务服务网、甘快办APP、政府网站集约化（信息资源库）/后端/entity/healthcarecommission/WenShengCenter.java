package com.hanweb.yyjr.zhangye.entity.healthcarecommission;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.gsszf.constant.Tables;
import io.swagger.annotations.ApiModelProperty;
import net.sf.oval.constraint.NotNull;

import java.math.BigInteger;

/**
 * 卫生中心数据 实体类
 * @author xueyize
 * @since 2022-02-24
 */
@Table(name= Tables.WenShengCenter,comment = "卫生中心数据")
public class WenShengCenter {

    @ApiModelProperty(value = "id", required = false)
    @NotNull
    @Column(name = "id", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "district", required = false)
    @NotNull
    @Column(name = "district", type = ColumnType.VARCHAR, length = 255, comment = "所在地区")
    private String district;

    @ApiModelProperty(value = "township", required = false)
    @NotNull
    @Column(name = "township", type = ColumnType.VARCHAR, length = 255, comment = "所在乡镇")
    private String townShip;

    @ApiModelProperty(value = "village", required = false)
    @NotNull
    @Column(name = "village", type = ColumnType.VARCHAR, length = 255, comment = "所在村")
    private String village;

    @ApiModelProperty(value = "name", required = false)
    @NotNull
    @Column(name = "name", type = ColumnType.VARCHAR, length = 255, comment = "单位名称")
    private String name;

    @ApiModelProperty(value = "address", required = false)
    @NotNull
    @Column(name = "address", type = ColumnType.VARCHAR, length = 255, comment = "详细地址")
    private String address;

    @ApiModelProperty(value = "fzr", required = false)
    @NotNull
    @Column(name = "fzr", type = ColumnType.VARCHAR, length = 255, comment = "负责人")
    private String fzr;

    @ApiModelProperty(value = "tele", required = false)
    @NotNull
    @Column(name = "tele", type = ColumnType.VARCHAR, length = 255, comment = "联系电话")
    private String tele;

    @ApiModelProperty(value = "type", required = false)
    @NotNull
    @Column(name = "type", type = ColumnType.VARCHAR, length = 255, comment = "单位类别")
    private String type;

    @ApiModelProperty(value = "state", required = false)
    @NotNull
    @Column(name = "state", type = ColumnType.VARCHAR, length = 255, comment = "单位状态")
    private String state;

    @ApiModelProperty(value = "isbranch", required = false)
    @NotNull
    @Column(name = "isbranch", type = ColumnType.VARCHAR, length = 255, comment = "是否分支")
    private String isBranch;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTownShip() {
        return townShip;
    }

    public void setTownShip(String townShip) {
        this.townShip = townShip;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFzr() {
        return fzr;
    }

    public void setFzr(String fzr) {
        this.fzr = fzr;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIsBranch() {
        return isBranch;
    }

    public void setIsBranch(String isBranch) {
        this.isBranch = isBranch;
    }
}
