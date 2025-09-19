package com.hanweb.yyjr.zhangye.entity.spad;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.gsszf.constant.Tables;
import io.swagger.annotations.ApiModelProperty;
import net.sf.oval.constraint.NotNull;

import java.math.BigInteger;

/**
 * 桥梁信息 实体类
 * @author xueyize
 * @since 2022-02-15
 */
@Table(name= Tables.SPAD_bridgeInfo,comment = "桥梁信息")
public class SpadBridgeInfo {

    @ApiModelProperty(value = "ID", required = false)
    @NotNull
    @Column(name = "ID", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "bridgename", required = false)
    @NotNull
    @Column(name = "bridgename", type = ColumnType.VARCHAR, length = 255, comment = "桥梁名称")
    private String bridgename;

    @ApiModelProperty(value = "bridgecode", required = false)
    @NotNull
    @Column(name = "bridgecode", type = ColumnType.VARCHAR, length = 255, comment = "桥梁代码")
    private String bridgecode;

    @ApiModelProperty(value = "qlzxzh", required = false)
    @NotNull
    @Column(name = "qlzxzh", type = ColumnType.VARCHAR, length = 255, comment = "桥梁中心桩号")
    private String qlzxzh;

    @ApiModelProperty(value = "lxbh", required = false)
    @NotNull
    @Column(name = "lxbh", type = ColumnType.VARCHAR, length = 255, comment = "路线编号")
    private String lxbh;

    @ApiModelProperty(value = "lxmc", required = false)
    @NotNull
    @Column(name = "lxmc", type = ColumnType.VARCHAR, length = 255, comment = "路线名称")
    private String lxmc;

    @ApiModelProperty(value = "jsdj", required = false)
    @NotNull
    @Column(name = "jsdj", type = ColumnType.VARCHAR, length = 255, comment = "技术等级")
    private String jsdj;

    @ApiModelProperty(value = "qlqc", required = false)
    @NotNull
    @Column(name = "qlqc", type = ColumnType.VARCHAR, length = 255, comment = "桥梁全长")
    private String qlqc;

    @ApiModelProperty(value = "kjzc", required = false)
    @NotNull
    @Column(name = "kjzc", type = ColumnType.VARCHAR, length = 255, comment = "跨径总长")
    private String kjzc;

    @ApiModelProperty(value = "dkzdkj", required = false)
    @NotNull
    @Column(name = "dkzdkj", type = ColumnType.VARCHAR, length = 255, comment = "单孔最大跨径")
    private String dkzdkj;

    @ApiModelProperty(value = "kjzh", required = false)
    @NotNull
    @Column(name = "kjzh", type = ColumnType.VARCHAR, length = 255, comment = "跨径组合")
    private String kjzh;

    @ApiModelProperty(value = "qlqk", required = false)
    @NotNull
    @Column(name = "qlqk", type = ColumnType.VARCHAR, length = 255, comment = "桥梁全宽")
    private String qlqk;

    @ApiModelProperty(value = "qmjk", required = false)
    @NotNull
    @Column(name = "qmjk", type = ColumnType.VARCHAR, length = 255, comment = "桥面净宽")
    private String qmjk;

    @ApiModelProperty(value = "kjdm", required = false)
    @NotNull
    @Column(name = "kjdm", type = ColumnType.VARCHAR, length = 255, comment = "跨径代码")
    private String kjdm;

    @ApiModelProperty(value = "kjlx", required = false)
    @NotNull
    @Column(name = "kjlx", type = ColumnType.VARCHAR, length = 255, comment = "跨径类型")
    private String kjlx;

    @ApiModelProperty(value = "nxdm", required = false)
    @NotNull
    @Column(name = "nxdm", type = ColumnType.VARCHAR, length = 255, comment = "年限代码")
    private String nxdm;

    @ApiModelProperty(value = "nxlx", required = false)
    @NotNull
    @Column(name = "nxlx", type = ColumnType.VARCHAR, length = 255, comment = "年限类型")
    private String nxlx;

    @ApiModelProperty(value = "jgdm", required = false)
    @NotNull
    @Column(name = "jgdm", type = ColumnType.VARCHAR, length = 255, comment = "结构代码")
    private String jgdm;

    @ApiModelProperty(value = "jglx", required = false)
    @NotNull
    @Column(name = "jglx", type = ColumnType.VARCHAR, length = 255, comment = "结构类型")
    private String jglx;

    @ApiModelProperty(value = "cldm", required = false)
    @NotNull
    @Column(name = "cldm", type = ColumnType.VARCHAR, length = 255, comment = "材料代码")
    private String cldm;

    @ApiModelProperty(value = "clmc", required = false)
    @NotNull
    @Column(name = "clmc", type = ColumnType.VARCHAR, length = 255, comment = "材料名称")
    private String clmc;

    @ApiModelProperty(value = "qddm", required = false)
    @NotNull
    @Column(name = "qddm", type = ColumnType.VARCHAR, length = 255, comment = "桥墩代码")
    private String qddm;

    @ApiModelProperty(value = "qdlx", required = false)
    @NotNull
    @Column(name = "qdlx", type = ColumnType.VARCHAR, length = 255, comment = "桥墩类型")
    private String qdlx;

    @ApiModelProperty(value = "hzdjdm", required = false)
    @NotNull
    @Column(name = "hzdjdm", type = ColumnType.VARCHAR, length = 255, comment = "荷载等级代码")
    private String hzdjdm;

    @ApiModelProperty(value = "hzdj", required = false)
    @NotNull
    @Column(name = "hzdj", type = ColumnType.VARCHAR, length = 255, comment = "荷载等级")
    private String hzdj;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getBridgename() {
        return bridgename;
    }

    public void setBridgename(String bridgename) {
        this.bridgename = bridgename;
    }

    public String getBridgecode() {
        return bridgecode;
    }

    public void setBridgecode(String bridgecode) {
        this.bridgecode = bridgecode;
    }

    public String getQlzxzh() {
        return qlzxzh;
    }

    public void setQlzxzh(String qlzxzh) {
        this.qlzxzh = qlzxzh;
    }

    public String getLxbh() {
        return lxbh;
    }

    public void setLxbh(String lxbh) {
        this.lxbh = lxbh;
    }

    public String getLxmc() {
        return lxmc;
    }

    public void setLxmc(String lxmc) {
        this.lxmc = lxmc;
    }

    public String getJsdj() {
        return jsdj;
    }

    public void setJsdj(String jsdj) {
        this.jsdj = jsdj;
    }

    public String getQlqc() {
        return qlqc;
    }

    public void setQlqc(String qlqc) {
        this.qlqc = qlqc;
    }

    public String getKjzc() {
        return kjzc;
    }

    public void setKjzc(String kjzc) {
        this.kjzc = kjzc;
    }

    public String getDkzdkj() {
        return dkzdkj;
    }

    public void setDkzdkj(String dkzdkj) {
        this.dkzdkj = dkzdkj;
    }

    public String getKjzh() {
        return kjzh;
    }

    public void setKjzh(String kjzh) {
        this.kjzh = kjzh;
    }

    public String getQlqk() {
        return qlqk;
    }

    public void setQlqk(String qlqk) {
        this.qlqk = qlqk;
    }

    public String getQmjk() {
        return qmjk;
    }

    public void setQmjk(String qmjk) {
        this.qmjk = qmjk;
    }

    public String getKjdm() {
        return kjdm;
    }

    public void setKjdm(String kjdm) {
        this.kjdm = kjdm;
    }

    public String getKjlx() {
        return kjlx;
    }

    public void setKjlx(String kjlx) {
        this.kjlx = kjlx;
    }

    public String getNxdm() {
        return nxdm;
    }

    public void setNxdm(String nxdm) {
        this.nxdm = nxdm;
    }

    public String getNxlx() {
        return nxlx;
    }

    public void setNxlx(String nxlx) {
        this.nxlx = nxlx;
    }

    public String getJgdm() {
        return jgdm;
    }

    public void setJgdm(String jgdm) {
        this.jgdm = jgdm;
    }

    public String getJglx() {
        return jglx;
    }

    public void setJglx(String jglx) {
        this.jglx = jglx;
    }

    public String getCldm() {
        return cldm;
    }

    public void setCldm(String cldm) {
        this.cldm = cldm;
    }

    public String getClmc() {
        return clmc;
    }

    public void setClmc(String clmc) {
        this.clmc = clmc;
    }

    public String getQddm() {
        return qddm;
    }

    public void setQddm(String qddm) {
        this.qddm = qddm;
    }

    public String getQdlx() {
        return qdlx;
    }

    public void setQdlx(String qdlx) {
        this.qdlx = qdlx;
    }

    public String getHzdjdm() {
        return hzdjdm;
    }

    public void setHzdjdm(String hzdjdm) {
        this.hzdjdm = hzdjdm;
    }

    public String getHzdj() {
        return hzdj;
    }

    public void setHzdj(String hzdj) {
        this.hzdj = hzdj;
    }
}
