package com.hanweb.yyjr.zhangye.entity.law;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.gsszf.constant.Tables;
import io.swagger.annotations.ApiModelProperty;
import net.sf.oval.constraint.NotNull;

import java.math.BigInteger;

/**
 * 公证机构信息 实体类
 * @author xueyize
 * @since 2022-02-24
 */
@Table(name= Tables.ZY_NotaryOrg,comment = "公证机构信息")
public class ZyNotaryOrg {

    @ApiModelProperty(value = "id", required = false)
    @NotNull
    @Column(name = "id", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "name", required = false)
    @NotNull
    @Column(name = "name", type = ColumnType.VARCHAR, length = 255, comment = "公证机构名")
    private String name;

    @ApiModelProperty(value = "tele", required = false)
    @NotNull
    @Column(name = "tele", type = ColumnType.VARCHAR, length = 255, comment = "联系电话")
    private String tele;

    @ApiModelProperty(value = "address", required = false)
    @NotNull
    @Column(name = "address", type = ColumnType.VARCHAR, length = 255, comment = "联系地址")
    private String address;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
