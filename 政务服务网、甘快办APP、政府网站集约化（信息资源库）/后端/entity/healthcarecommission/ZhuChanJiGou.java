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
 * 助产机构信息 实体类
 * @author xueyize
 * @since 2022-02-24
 */
@Table(name= Tables.ZhuChanJiGou,comment = "助产机构信息")
public class ZhuChanJiGou {

    @ApiModelProperty(value = "id", required = false)
    @NotNull
    @Column(name = "id", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "hospname", required = false)
    @NotNull
    @Column(name = "hospname", type = ColumnType.VARCHAR, length = 255, comment = "医院名称")
    private String hospname;

    @ApiModelProperty(value = "frdb", required = false)
    @NotNull
    @Column(name = "frdb", type = ColumnType.VARCHAR, length = 255, comment = "法人代表")
    private String frdb;

    @ApiModelProperty(value = "address", required = false)
    @NotNull
    @Column(name = "address", type = ColumnType.VARCHAR, length = 255, comment = "联系地址")
    private String address;

    @ApiModelProperty(value = "tele", required = false)
    @NotNull
    @Column(name = "tele", type = ColumnType.VARCHAR, length = 255, comment = "联系电话")
    private String tele;

    @ApiModelProperty(value = "beizhu", required = false)
    @NotNull
    @Column(name = "beizhu", type = ColumnType.VARCHAR, length = 255, comment = "备注")
    private String beizhu;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getHospname() {
        return hospname;
    }

    public void setHospname(String hospname) {
        this.hospname = hospname;
    }

    public String getFrdb() {
        return frdb;
    }

    public void setFrdb(String frdb) {
        this.frdb = frdb;
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

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }
}
