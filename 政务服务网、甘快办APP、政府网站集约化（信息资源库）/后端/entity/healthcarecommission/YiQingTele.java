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
 * 疫情防控联系电话 实体类
 * @author xueyize
 * @since 2022-02-24
 */
@Table(name= Tables.YiQingTele,comment = "疫情防控联系电话")
public class YiQingTele {

    @ApiModelProperty(value = "id", required = false)
    @NotNull
    @Column(name = "id", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "area", required = false)
    @NotNull
    @Column(name = "area", type = ColumnType.VARCHAR, length = 255, comment = "所属地区")
    private String area;

    @ApiModelProperty(value = "kftele", required = false)
    @NotNull
    @Column(name = "kftele", type = ColumnType.VARCHAR, length = 255, comment = "客服联系电话")
    private String kftele;

    @ApiModelProperty(value = "zbtele", required = false)
    @NotNull
    @Column(name = "zbtele", type = ColumnType.VARCHAR, length = 255, comment = "值班联系电话")
    private String zbtele;

    @ApiModelProperty(value = "fax", required = false)
    @NotNull
    @Column(name = "fax", type = ColumnType.VARCHAR, length = 255, comment = "传真")
    private String fax;

    @ApiModelProperty(value = "lxr", required = false)
    @NotNull
    @Column(name = "lxr", type = ColumnType.VARCHAR, length = 255, comment = "联系人")
    private String lxr;

    @ApiModelProperty(value = "lxrtele", required = false)
    @NotNull
    @Column(name = "lxrtele", type = ColumnType.VARCHAR, length = 255, comment = "联系人电话")
    private String lxrtele;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getKftele() {
        return kftele;
    }

    public void setKftele(String kftele) {
        this.kftele = kftele;
    }

    public String getZbtele() {
        return zbtele;
    }

    public void setZbtele(String zbtele) {
        this.zbtele = zbtele;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getLxr() {
        return lxr;
    }

    public void setLxr(String lxr) {
        this.lxr = lxr;
    }

    public String getLxrtele() {
        return lxrtele;
    }

    public void setLxrtele(String lxrtele) {
        this.lxrtele = lxrtele;
    }
}
