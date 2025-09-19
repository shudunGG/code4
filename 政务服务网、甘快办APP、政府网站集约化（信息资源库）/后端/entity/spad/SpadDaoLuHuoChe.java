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
 * 交委道路货车 实体类
 * @author xueyize
 * @since 2022-02-15
 */
@Table(name= Tables.SPAD_daoluhuoche,comment = "交委道路货车")
public class SpadDaoLuHuoChe {

    @ApiModelProperty(value = "ID", required = false)
    @NotNull
    @Column(name = "ID", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "jczmc", required = false)
    @NotNull
    @Column(name = "jczmc", type = ColumnType.VARCHAR, length = 255, comment = "监测站名称")
    private String jczmc;

    @ApiModelProperty(value = "jczxz", required = false)
    @NotNull
    @Column(name = "jczxz", type = ColumnType.VARCHAR, length = 255, comment = "监测站性质")
    private String jczxz;

    @ApiModelProperty(value = "jzsj", required = false)
    @NotNull
    @Column(name = "jzsj", type = ColumnType.VARCHAR, length = 255, comment = "建站时间")
    private String jzsj;

    @ApiModelProperty(value = "fzr", required = false)
    @NotNull
    @Column(name = "fzr", type = ColumnType.VARCHAR, length = 255, comment = "负责人")
    private String fzr;

    @ApiModelProperty(value = "lxdh", required = false)
    @NotNull
    @Column(name = "lxdh", type = ColumnType.VARCHAR, length = 255, comment = "联系电话")
    private String lxdh;

    @ApiModelProperty(value = "dlxkz", required = false)
    @NotNull
    @Column(name = "dlxkz", type = ColumnType.VARCHAR, length = 255, comment = "道路许可证")
    private String dlxkz;

    @ApiModelProperty(value = "zzjg", required = false)
    @NotNull
    @Column(name = "zzjg", type = ColumnType.VARCHAR, length = 255, comment = "组织机构")
    private String zzjg;

    @ApiModelProperty(value = "renshu", required = false)
    @NotNull
    @Column(name = "renshu", type = ColumnType.VARCHAR, length = 255, comment = "人数")
    private String renshu;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getJczmc() {
        return jczmc;
    }

    public void setJczmc(String jczmc) {
        this.jczmc = jczmc;
    }

    public String getJczxz() {
        return jczxz;
    }

    public void setJczxz(String jczxz) {
        this.jczxz = jczxz;
    }

    public String getJzsj() {
        return jzsj;
    }

    public void setJzsj(String jzsj) {
        this.jzsj = jzsj;
    }

    public String getFzr() {
        return fzr;
    }

    public void setFzr(String fzr) {
        this.fzr = fzr;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getDlxkz() {
        return dlxkz;
    }

    public void setDlxkz(String dlxkz) {
        this.dlxkz = dlxkz;
    }

    public String getZzjg() {
        return zzjg;
    }

    public void setZzjg(String zzjg) {
        this.zzjg = zzjg;
    }

    public String getRenshu() {
        return renshu;
    }

    public void setRenshu(String renshu) {
        this.renshu = renshu;
    }
}
