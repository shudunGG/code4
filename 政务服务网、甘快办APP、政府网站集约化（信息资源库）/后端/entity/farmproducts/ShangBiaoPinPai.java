package com.hanweb.yyjr.zhangye.entity.farmproducts;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.gsszf.constant.Tables;
import io.swagger.annotations.ApiModelProperty;
import net.sf.oval.constraint.NotNull;

import java.math.BigInteger;

/**
 * 农产品商标品牌 实体类
 * @author xueyize
 * @since 2022-02-15
 */
@Table(name= Tables.ZY_ShangBiaoPinPai,comment = "农产品商标品牌")
public class ShangBiaoPinPai {

    @ApiModelProperty(value = "ID", required = false)
    @NotNull
    @Column(name = "ID", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "brandname", required = false)
    @NotNull
    @Column(name = "brandname", type = ColumnType.VARCHAR, length = 255, comment = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "type", required = false)
    @NotNull
    @Column(name = "type", type = ColumnType.VARCHAR, length = 255, comment = "产品种类")
    private String type;

    @ApiModelProperty(value = "companyname", required = false)
    @NotNull
    @Column(name = "companyname", type = ColumnType.VARCHAR, length = 255, comment = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "county", required = false)
    @NotNull
    @Column(name = "county", type = ColumnType.VARCHAR, length = 255, comment = "所属区县")
    private String county;

    @ApiModelProperty(value = "time", required = false)
    @NotNull
    @Column(name = "time", type = ColumnType.VARCHAR, length = 255, comment = "认定时间")
    private String time;

    @ApiModelProperty(value = "beizhu", required = false)
    @NotNull
    @Column(name = "beizhu", type = ColumnType.VARCHAR, length = 255, comment = "备注")
    private String beiZhu;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBeiZhu() {
        return beiZhu;
    }

    public void setBeiZhu(String beiZhu) {
        this.beiZhu = beiZhu;
    }
}
