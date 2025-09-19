package com.hanweb.yyjr.zhangye.entity.weatherbureau;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.gsszf.constant.Tables;
import io.swagger.annotations.ApiModelProperty;
import net.sf.oval.constraint.NotNull;

import java.math.BigInteger;

/**
 * 气象局防雷检测 实体类
 * @author xueyize
 * @since 2022-02-15
 */
@Table(name= Tables.WB_fangleijiance,comment = "气象局防雷检测")
public class WbFangLeiJianCe {

    @ApiModelProperty(value = "ID", required = false)
    @NotNull
    @Column(name = "ID", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "dwmc", required = false)
    @NotNull
    @Column(name = "dwmc", type = ColumnType.VARCHAR, length = 255, comment = "单位名称")
    private String dwmc;

    @ApiModelProperty(value = "szsf", required = false)
    @NotNull
    @Column(name = "szsf", type = ColumnType.VARCHAR, length = 255, comment = "所在省份")
    private String szsf;

    @ApiModelProperty(value = "szds", required = false)
    @NotNull
    @Column(name = "szds", type = ColumnType.VARCHAR, length = 255, comment = "所在地市")
    private String szds;

    @ApiModelProperty(value = "szqx", required = false)
    @NotNull
    @Column(name = "szqx", type = ColumnType.VARCHAR, length = 255, comment = "所在区县")
    private String szqx;

    @ApiModelProperty(value = "zzdj", required = false)
    @NotNull
    @Column(name = "zzdj", type = ColumnType.VARCHAR, length = 255, comment = "资质等级")
    private String zzdj;

    @ApiModelProperty(value = "fzjg", required = false)
    @NotNull
    @Column(name = "fzjg", type = ColumnType.VARCHAR, length = 255, comment = "分支机构")
    private String fzjg;

    @ApiModelProperty(value = "rysl", required = false)
    @NotNull
    @Column(name = "rysl", type = ColumnType.VARCHAR, length = 255, comment = "人员数量")
    private String rysl;

    @ApiModelProperty(value = "bgsl", required = false)
    @NotNull
    @Column(name = "bgsl", type = ColumnType.VARCHAR, length = 255, comment = "报告数量")
    private String bgsl;

    @ApiModelProperty(value = "ndbg", required = false)
    @NotNull
    @Column(name = "ndbg", type = ColumnType.VARCHAR, length = 255, comment = "年度报告")
    private String ndbg;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getDwmc() {
        return dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }

    public String getSzsf() {
        return szsf;
    }

    public void setSzsf(String szsf) {
        this.szsf = szsf;
    }

    public String getSzds() {
        return szds;
    }

    public void setSzds(String szds) {
        this.szds = szds;
    }

    public String getSzqx() {
        return szqx;
    }

    public void setSzqx(String szqx) {
        this.szqx = szqx;
    }

    public String getZzdj() {
        return zzdj;
    }

    public void setZzdj(String zzdj) {
        this.zzdj = zzdj;
    }

    public String getFzjg() {
        return fzjg;
    }

    public void setFzjg(String fzjg) {
        this.fzjg = fzjg;
    }

    public String getRysl() {
        return rysl;
    }

    public void setRysl(String rysl) {
        this.rysl = rysl;
    }

    public String getBgsl() {
        return bgsl;
    }

    public void setBgsl(String bgsl) {
        this.bgsl = bgsl;
    }

    public String getNdbg() {
        return ndbg;
    }

    public void setNdbg(String ndbg) {
        this.ndbg = ndbg;
    }
}
