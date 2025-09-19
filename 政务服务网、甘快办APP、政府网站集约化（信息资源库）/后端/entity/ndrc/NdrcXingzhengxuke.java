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
 * 发改委行政许可 实体类
 * @author xueyize
 * @since 25522-02-15
 */
@Table(name= Tables.NDRC_xingzhengxuke,comment = "发改委权责清单")
public class NdrcXingzhengxuke {

    @ApiModelProperty(value = "ID", required = false)
    @NotNull
    @Column(name = "ID", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "xzxdrmc", required = false)
    @NotNull
    @Column(name = "xzxdrmc", type = ColumnType.VARCHAR, length = 255, comment = "行政相对人名称")
    private String xzxdrmc;

    @ApiModelProperty(value = "xzxdrdm", required = false)
    @NotNull
    @Column(name = "xzxdrdm", type = ColumnType.VARCHAR, length = 255, comment = "行政相对人代码")
    private String xzxdrdm;

    @ApiModelProperty(value = "fdr", required = false)
    @NotNull
    @Column(name = "fdr", type = ColumnType.VARCHAR, length = 255, comment = "法定人")
    private String fdr;

    @ApiModelProperty(value = "fdrsfz", required = false)
    @NotNull
    @Column(name = "fdrsfz", type = ColumnType.VARCHAR, length = 255, comment = "法定人身份证")
    private String fdrsfz;

    @ApiModelProperty(value = "xzxkjdwsmc", required = false)
    @NotNull
    @Column(name = "xzxkjdwsmc", type = ColumnType.TEXT, comment = "行政许可决定文书名称")
    private String xzxkjdwsmc;

    @ApiModelProperty(value = "xzxkjdwsh", required = false)
    @NotNull
    @Column(name = "xzxkjdwsh", type = ColumnType.VARCHAR, length = 255, comment = "行政许可决定文书号")
    private String xzxkjdwsh;

    @ApiModelProperty(value = "xklb", required = false)
    @NotNull
    @Column(name = "xklb", type = ColumnType.VARCHAR, length = 255, comment = "许可类别")
    private String xklb;

    @ApiModelProperty(value = "xkzsmc", required = false)
    @NotNull
    @Column(name = "xkzsmc", type = ColumnType.VARCHAR, length = 255, comment = "许可证书名称")
    private String xkzsmc;

    @ApiModelProperty(value = "xkbh", required = false)
    @NotNull
    @Column(name = "xkbh", type = ColumnType.VARCHAR, length = 255, comment = "许可编号")
    private String xkbh;

    @ApiModelProperty(value = "xknr", required = false)
    @NotNull
    @Column(name = "xknr", type = ColumnType.VARCHAR, length = 255, comment = "许可内容")
    private String xknr;

    @ApiModelProperty(value = "xkjdrq", required = false)
    @NotNull
    @Column(name = "xkjdrq", type = ColumnType.VARCHAR, length = 255, comment = "许可决定日期")
    private String xkjdrq;

    @ApiModelProperty(value = "yxqz", required = false)
    @NotNull
    @Column(name = "yxqz", type = ColumnType.VARCHAR, length = 255, comment = "有效期自")
    private String yxqz;

    @ApiModelProperty(value = "yxqd", required = false)
    @NotNull
    @Column(name = "yxqd", type = ColumnType.VARCHAR, length = 255, comment = "有效期到")
    private String yxqd;

    @ApiModelProperty(value = "xkjg", required = false)
    @NotNull
    @Column(name = "xkjg", type = ColumnType.VARCHAR, length = 255, comment = "许可机关")
    private String xkjg;

    @ApiModelProperty(value = "xkjgtydm", required = false)
    @NotNull
    @Column(name = "xkjgtydm", type = ColumnType.VARCHAR, length = 255, comment = "许可机关统一代码")
    private String xkjgtydm;

    @ApiModelProperty(value = "dqzt", required = false)
    @NotNull
    @Column(name = "dqzt", type = ColumnType.VARCHAR, length = 255, comment = "当前状态")
    private String dqzt;

    @ApiModelProperty(value = "sjlydw", required = false)
    @NotNull
    @Column(name = "sjlydw", type = ColumnType.VARCHAR, length = 255, comment = "数据来源单位")
    private String sjlydw;

    @ApiModelProperty(value = "sjlydwdm", required = false)
    @NotNull
    @Column(name = "sjlydwdm", type = ColumnType.VARCHAR, length = 255, comment = "数据来源单位代码")
    private String sjlydwdm;

    @ApiModelProperty(value = "lrr", required = false)
    @NotNull
    @Column(name = "lrr", type = ColumnType.VARCHAR, length = 255, comment = "录入人")
    private String lrr;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getXzxdrmc() {
        return xzxdrmc;
    }

    public void setXzxdrmc(String xzxdrmc) {
        this.xzxdrmc = xzxdrmc;
    }

    public String getXzxdrdm() {
        return xzxdrdm;
    }

    public void setXzxdrdm(String xzxdrdm) {
        this.xzxdrdm = xzxdrdm;
    }

    public String getXzxkjdwsmc() {
        return xzxkjdwsmc;
    }

    public void setXzxkjdwsmc(String xzxkjdwsmc) {
        this.xzxkjdwsmc = xzxkjdwsmc;
    }

    public String getXzxkjdwsh() {
        return xzxkjdwsh;
    }

    public void setXzxkjdwsh(String xzxkjdwsh) {
        this.xzxkjdwsh = xzxkjdwsh;
    }

    public String getXklb() {
        return xklb;
    }

    public void setXklb(String xklb) {
        this.xklb = xklb;
    }

    public String getXkzsmc() {
        return xkzsmc;
    }

    public void setXkzsmc(String xkzsmc) {
        this.xkzsmc = xkzsmc;
    }

    public String getXkbh() {
        return xkbh;
    }

    public void setXkbh(String xkbh) {
        this.xkbh = xkbh;
    }

    public String getXknr() {
        return xknr;
    }

    public void setXknr(String xknr) {
        this.xknr = xknr;
    }

    public String getXkjdrq() {
        return xkjdrq;
    }

    public void setXkjdrq(String xkjdrq) {
        this.xkjdrq = xkjdrq;
    }

    public String getYxqz() {
        return yxqz;
    }

    public void setYxqz(String yxqz) {
        this.yxqz = yxqz;
    }

    public String getYxqd() {
        return yxqd;
    }

    public void setYxqd(String yxqd) {
        this.yxqd = yxqd;
    }

    public String getXkjg() {
        return xkjg;
    }

    public void setXkjg(String xkjg) {
        this.xkjg = xkjg;
    }

    public String getXkjgtydm() {
        return xkjgtydm;
    }

    public void setXkjgtydm(String xkjgtydm) {
        this.xkjgtydm = xkjgtydm;
    }

    public String getDqzt() {
        return dqzt;
    }

    public void setDqzt(String dqzt) {
        this.dqzt = dqzt;
    }

    public String getSjlydw() {
        return sjlydw;
    }

    public void setSjlydw(String sjlydw) {
        this.sjlydw = sjlydw;
    }

    public String getSjlydwdm() {
        return sjlydwdm;
    }

    public void setSjlydwdm(String sjlydwdm) {
        this.sjlydwdm = sjlydwdm;
    }

    public String getLrr() {
        return lrr;
    }

    public void setLrr(String lrr) {
        this.lrr = lrr;
    }

    public String getFdr() {
        return fdr;
    }

    public void setFdr(String fdr) {
        this.fdr = fdr;
    }

    public String getFdrsfz() {
        return fdrsfz;
    }

    public void setFdrsfz(String fdrsfz) {
        this.fdrsfz = fdrsfz;
    }
}
