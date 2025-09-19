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
 * 农业龙头企业 实体类
 * @author xueyize
 * @since 2022-02-15
 */
@Table(name= Tables.ZY_LongHeader,comment = "农业龙头企业")
public class LongHeader {

    @ApiModelProperty(value = "ID", required = false)
    @NotNull
    @Column(name = "ID", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "countyname", required = false)
    @NotNull
    @Column(name = "countyname", type = ColumnType.VARCHAR, length = 255, comment = "县名称")
    private String countyName;

    @ApiModelProperty(value = "companyname", required = false)
    @NotNull
    @Column(name = "companyname", type = ColumnType.VARCHAR, length = 255, comment = "企业名称")
    private String companyName;

    @ApiModelProperty(value = "socialcode", required = false)
    @NotNull
    @Column(name = "socialcode", type = ColumnType.VARCHAR, length = 255, comment = "社会信用代码")
    private String socialCode;

    @ApiModelProperty(value = "address", required = false)
    @NotNull
    @Column(name = "address", type = ColumnType.VARCHAR, length = 255, comment = "详细地址")
    private String address;

    @ApiModelProperty(value = "level", required = false)
    @NotNull
    @Column(name = "level", type = ColumnType.VARCHAR, length = 255, comment = "认定级别")
    private String level;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSocialCode() {
        return socialCode;
    }

    public void setSocialCode(String socialCode) {
        this.socialCode = socialCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
