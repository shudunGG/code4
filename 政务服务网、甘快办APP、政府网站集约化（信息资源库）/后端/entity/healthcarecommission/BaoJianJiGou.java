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
 * 保健机构信息 实体类
 * @author xueyize
 * @since 2022-02-24
 */
@Table(name= Tables.BaoJianJiGou,comment = "保健机构信息")
public class BaoJianJiGou {

    @ApiModelProperty(value = "id", required = false)
    @NotNull
    @Column(name = "id", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "healthcarename", required = false)
    @NotNull
    @Column(name = "healthcarename", type = ColumnType.VARCHAR, length = 255, comment = "保健院名称")
    private String healthAreName;

    @ApiModelProperty(value = "frdb", required = false)
    @NotNull
    @Column(name = "frdb", type = ColumnType.VARCHAR, length = 255, comment = "法人代表")
    private String frdb;

    @ApiModelProperty(value = "address", required = false)
    @NotNull
    @Column(name = "address", type = ColumnType.VARCHAR, length = 255, comment = "地址")
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

    public String getHealthAreName() {
        return healthAreName;
    }

    public void setHealthAreName(String healthAreName) {
        this.healthAreName = healthAreName;
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
