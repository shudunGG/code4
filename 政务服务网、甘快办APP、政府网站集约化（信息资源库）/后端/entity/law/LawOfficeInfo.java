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
 * 律师事务所信息 实体类
 * @author xueyize
 * @since 2022-02-24
 */
@Table(name= Tables.LawOfficeInfo,comment = "律师事务所信息")
public class LawOfficeInfo {

    @ApiModelProperty(value = "id", required = false)
    @NotNull
    @Column(name = "id", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "lawofficename", required = false)
    @NotNull
    @Column(name = "lawofficename", type = ColumnType.VARCHAR, length = 255, comment = "律师服务所名称")
    private String lawOfficeName;

    @ApiModelProperty(value = "tele", required = false)
    @NotNull
    @Column(name = "tele", type = ColumnType.VARCHAR, length = 255, comment = "联系电话")
    private String tele;

    @ApiModelProperty(value = "address", required = false)
    @NotNull
    @Column(name = "address", type = ColumnType.VARCHAR, length = 255, comment = "地址")
    private String address;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getLawOfficeName() {
        return lawOfficeName;
    }

    public void setLawOfficeName(String lawOfficeName) {
        this.lawOfficeName = lawOfficeName;
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
