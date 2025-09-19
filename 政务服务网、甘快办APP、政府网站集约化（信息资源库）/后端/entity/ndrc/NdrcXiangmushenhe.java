package com.hanweb.yyjr.zhangye.entity.ndrc;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.gsszf.constant.Tables;
import io.swagger.annotations.ApiModelProperty;
import net.sf.oval.constraint.NotNull;

import java.math.BigInteger;

/**
 * 发改委项目审核实体类
 * @author xueyize
 * @since 2022-02-15
 */
@Table(name= Tables.NDRC_xiangmushenhe,comment = "发改委权责清单")
public class NdrcXiangmushenhe {

    @ApiModelProperty(value = "ID", required = false)
    @NotNull
    @Column(name = "ID", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "xmdm", required = false)
    @NotNull
    @Column(name = "xmdm", type = ColumnType.VARCHAR, length = 255, comment = "项目代码")
    private String xmdm;

    @ApiModelProperty(value = "xmmc", required = false)
    @NotNull
    @Column(name = "xmmc", type = ColumnType.VARCHAR, length = 255, comment = "项目名称")
    private String xmmc;

    @ApiModelProperty(value = "shlx", required = false)
    @NotNull
    @Column(name = "shlx", type = ColumnType.VARCHAR, length = 255, comment = "审核类型")
    private String shlx;

    @ApiModelProperty(value = "nwzlx", required = false)
    @NotNull
    @Column(name = "nwzlx", type = ColumnType.VARCHAR, length = 255, comment = "内外资类型")
    private String nwzlx;

    @ApiModelProperty(value = "xzqhmc", required = false)
    @NotNull
    @Column(name = "xzqhmc", type = ColumnType.VARCHAR, length = 255, comment = "行政区划名称")
    private String xzqhmc;

    @ApiModelProperty(value = "spjgmc", required = false)
    @NotNull
    @Column(name = "spjgmc", type = ColumnType.VARCHAR, length = 255, comment = "审批机关名称")
    private String spjgmc;

    @ApiModelProperty(value = "spcj", required = false)
    @NotNull
    @Column(name = "spcj", type = ColumnType.VARCHAR, length = 255, comment = "审批层级")
    private String spcj;

    @ApiModelProperty(value = "xmcnfm", required = false)
    @NotNull
    @Column(name = "xmcnfm", type = ColumnType.VARCHAR, length = 255, comment = "项目承诺赋码")
    private String xmcnfm;

    @ApiModelProperty(value = "gbhymc", required = false)
    @NotNull
    @Column(name = "gbhymc", type = ColumnType.VARCHAR, length = 255, comment = "国标行业名称")
    private String gbhymc;

    @ApiModelProperty(value = "jsgm", required = false)
    @NotNull
    @Column(name = "jsgm", type = ColumnType.VARCHAR, length = 255, comment = "建设规模")
    private String jsgm;

    @ApiModelProperty(value = "kgsj", required = false)
    @NotNull
    @Column(name = "kgsj", type = ColumnType.VARCHAR, length = 255, comment = "开工时间")
    private String kgsj;

    @ApiModelProperty(value = "jgsj", required = false)
    @NotNull
    @Column(name = "jgsj", type = ColumnType.VARCHAR, length = 255, comment = "竣工时间")
    private String jgsj;

    @ApiModelProperty(value = "sbdwmc", required = false)
    @NotNull
    @Column(name = "sbdwmc", type = ColumnType.VARCHAR, length = 255, comment = "申报单位名称")
    private String sbdwmc;

    @ApiModelProperty(value = "frdwmc", required = false)
    @NotNull
    @Column(name = "frdwmc", type = ColumnType.VARCHAR, length = 255, comment = "法人单位名称")
    private String frdwmc;

    @ApiModelProperty(value = "xmfrzz", required = false)
    @NotNull
    @Column(name = "xmfrzz", type = ColumnType.VARCHAR, length = 255, comment = "项目法人证照")
    private String xmfrzz;

    @ApiModelProperty(value = "fddbr", required = false)
    @NotNull
    @Column(name = "fddbr", type = ColumnType.VARCHAR, length = 255, comment = "法定代表人")
    private String fddbr;

    @ApiModelProperty(value = "fddbrsjh", required = false)
    @NotNull
    @Column(name = "fddbrsjh", type = ColumnType.VARCHAR, length = 255, comment = "法定代表人手机号")
    private String fddbrsjh;

    @ApiModelProperty(value = "fzrxm", required = false)
    @NotNull
    @Column(name = "fzrxm", type = ColumnType.VARCHAR, length = 255, comment = "负责人姓名")
    private String fzrxm;

    @ApiModelProperty(value = "fzrsjh", required = false)
    @NotNull
    @Column(name = "fzrsjh", type = ColumnType.VARCHAR, length = 255, comment = "负责人手机号")
    private String fzrsjh;

    @ApiModelProperty(value = "lxrxm", required = false)
    @NotNull
    @Column(name = "lxrxm", type = ColumnType.VARCHAR, length = 255, comment = "联系人姓名")
    private String lxrxm;

    @ApiModelProperty(value = "lxrsjh", required = false)
    @NotNull
    @Column(name = "lxrsjh", type = ColumnType.VARCHAR, length = 255, comment = "联系人手机号")
    private String lxrsjh;

    @ApiModelProperty(value = "xxssdq", required = false)
    @NotNull
    @Column(name = "xxssdq", type = ColumnType.VARCHAR, length = 255, comment = "详细所属地区")
    private String xxssdq;

    @ApiModelProperty(value = "xxssdqmc", required = false)
    @NotNull
    @Column(name = "xxssdqmc", type = ColumnType.VARCHAR, length = 255, comment = "详细所属地区名称")
    private String xxssdqmc;

    @ApiModelProperty(value = "xmxxdz", required = false)
    @NotNull
    @Column(name = "xmxxdz", type = ColumnType.VARCHAR, length = 255, comment = "项目详细地址")
    private String xmxxdz;

    @ApiModelProperty(value = "ztzje", required = false)
    @NotNull
    @Column(name = "ztzje", type = ColumnType.VARCHAR, length = 255, comment = "总投资金额")
    private String ztzje;

    @ApiModelProperty(value = "sbsj", required = false)
    @NotNull
    @Column(name = "sbsj", type = ColumnType.VARCHAR, length = 255, comment = "申报时间")
    private String sbsj;

    @ApiModelProperty(value = "fmsj", required = false)
    @NotNull
    @Column(name = "fmsj", type = ColumnType.VARCHAR, length = 255, comment = "赋码时间")
    private String fmsj;

    @ApiModelProperty(value = "shjg", required = false)
    @NotNull
    @Column(name = "shjg", type = ColumnType.VARCHAR, length = 255, comment = "审核机关")
    private String shjg;

    @ApiModelProperty(value = "spwh", required = false)
    @NotNull
    @Column(name = "spwh", type = ColumnType.VARCHAR, length = 255, comment = "审核文号")
    private String spwh;

    @ApiModelProperty(value = "shsj", required = false)
    @NotNull
    @Column(name = "shsj", type = ColumnType.VARCHAR, length = 255, comment = "审核时间")
    private String shsj;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getXmdm() {
        return xmdm;
    }

    public void setXmdm(String xmdm) {
        this.xmdm = xmdm;
    }

    public String getXmmc() {
        return xmmc;
    }

    public void setXmmc(String xmmc) {
        this.xmmc = xmmc;
    }

    public String getShlx() {
        return shlx;
    }

    public void setShlx(String shlx) {
        this.shlx = shlx;
    }

    public String getNwzlx() {
        return nwzlx;
    }

    public void setNwzlx(String nwzlx) {
        this.nwzlx = nwzlx;
    }

    public String getXzqhmc() {
        return xzqhmc;
    }

    public void setXzqhmc(String xzqhmc) {
        this.xzqhmc = xzqhmc;
    }

    public String getSpjgmc() {
        return spjgmc;
    }

    public void setSpjgmc(String spjgmc) {
        this.spjgmc = spjgmc;
    }

    public String getXmcnfm() {
        return xmcnfm;
    }

    public void setXmcnfm(String xmcnfm) {
        this.xmcnfm = xmcnfm;
    }

    public String getGbhymc() {
        return gbhymc;
    }

    public void setGbhymc(String gbhymc) {
        this.gbhymc = gbhymc;
    }

    public String getJsgm() {
        return jsgm;
    }

    public void setJsgm(String jsgm) {
        this.jsgm = jsgm;
    }

    public String getKgsj() {
        return kgsj;
    }

    public void setKgsj(String kgsj) {
        this.kgsj = kgsj;
    }

    public String getJgsj() {
        return jgsj;
    }

    public void setJgsj(String jgsj) {
        this.jgsj = jgsj;
    }

    public String getSbdwmc() {
        return sbdwmc;
    }

    public void setSbdwmc(String sbdwmc) {
        this.sbdwmc = sbdwmc;
    }

    public String getFrdwmc() {
        return frdwmc;
    }

    public void setFrdwmc(String frdwmc) {
        this.frdwmc = frdwmc;
    }

    public String getXmfrzz() {
        return xmfrzz;
    }

    public void setXmfrzz(String xmfrzz) {
        this.xmfrzz = xmfrzz;
    }

    public String getFddbr() {
        return fddbr;
    }

    public void setFddbr(String fddbr) {
        this.fddbr = fddbr;
    }

    public String getFddbrsjh() {
        return fddbrsjh;
    }

    public void setFddbrsjh(String fddbrsjh) {
        this.fddbrsjh = fddbrsjh;
    }

    public String getFzrxm() {
        return fzrxm;
    }

    public void setFzrxm(String fzrxm) {
        this.fzrxm = fzrxm;
    }

    public String getFzrsjh() {
        return fzrsjh;
    }

    public void setFzrsjh(String fzrsjh) {
        this.fzrsjh = fzrsjh;
    }

    public String getLxrxm() {
        return lxrxm;
    }

    public void setLxrxm(String lxrxm) {
        this.lxrxm = lxrxm;
    }

    public String getLxrsjh() {
        return lxrsjh;
    }

    public void setLxrsjh(String lxrsjh) {
        this.lxrsjh = lxrsjh;
    }

    public String getXxssdq() {
        return xxssdq;
    }

    public void setXxssdq(String xxssdq) {
        this.xxssdq = xxssdq;
    }

    public String getXxssdqmc() {
        return xxssdqmc;
    }

    public void setXxssdqmc(String xxssdqmc) {
        this.xxssdqmc = xxssdqmc;
    }

    public String getXmxxdz() {
        return xmxxdz;
    }

    public void setXmxxdz(String xmxxdz) {
        this.xmxxdz = xmxxdz;
    }

    public String getZtzje() {
        return ztzje;
    }

    public void setZtzje(String ztzje) {
        this.ztzje = ztzje;
    }

    public String getSbsj() {
        return sbsj;
    }

    public void setSbsj(String sbsj) {
        this.sbsj = sbsj;
    }

    public String getFmsj() {
        return fmsj;
    }

    public void setFmsj(String fmsj) {
        this.fmsj = fmsj;
    }

    public String getShjg() {
        return shjg;
    }

    public void setShjg(String shjg) {
        this.shjg = shjg;
    }

    public String getSpwh() {
        return spwh;
    }

    public void setSpwh(String spwh) {
        this.spwh = spwh;
    }

    public String getShsj() {
        return shsj;
    }

    public void setShsj(String shsj) {
        this.shsj = shsj;
    }

    public String getSpcj() {
        return spcj;
    }

    public void setSpcj(String spcj) {
        this.spcj = spcj;
    }
}
