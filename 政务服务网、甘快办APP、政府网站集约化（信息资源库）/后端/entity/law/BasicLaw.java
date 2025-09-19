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
 * 基层法律服务所信息 实体类
 * @author xueyize
 * @since 2022-02-24
 */
@Table(name= Tables.BasicLaw,comment = "基层法律服务所信息")
public class BasicLaw {

    @ApiModelProperty(value = "id", required = false)
    @NotNull
    @Column(name = "id", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "legalofficename", required = false)
    @NotNull
    @Column(name = "legalofficename", type = ColumnType.VARCHAR, length = 255, comment = "法律服务所名称")
    private String legalOfficeName;

    @ApiModelProperty(value = "address", required = false)
    @NotNull
    @Column(name = "address", type = ColumnType.VARCHAR, length = 255, comment = "地址")
    private String address;

    @ApiModelProperty(value = "tele", required = false)
    @NotNull
    @Column(name = "tele", type = ColumnType.VARCHAR, length = 255, comment = "电话")
    private String tele;


    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getLegalOfficeName() {
        return legalOfficeName;
    }

    public void setLegalOfficeName(String legalOfficeName) {
        this.legalOfficeName = legalOfficeName;
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
}
