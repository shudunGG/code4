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
 * 福彩站点 实体类
 * @author xueyize
 * @since 2022-03-25
 */
@Table(name= Tables.ZY_FuCaiZhanDian,comment = "福彩站点")
public class FuCaiZhanDian {

    @ApiModelProperty(value = "id", required = false)
    @NotNull
    @Column(name = "id", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "siteid", required = false)
    @NotNull
    @Column(name = "siteid", type = ColumnType.VARCHAR, length = 255, comment = "站点编号")
    private String siteId;

    @ApiModelProperty(value = "sitearea", required = false)
    @NotNull
    @Column(name = "sitearea", type = ColumnType.VARCHAR, length = 255, comment = "站点地址")
    private String siteArea;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteArea() {
        return siteArea;
    }

    public void setSiteArea(String siteArea) {
        this.siteArea = siteArea;
    }
}
