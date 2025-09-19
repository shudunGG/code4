package com.hanweb.yyjr.zhangye.entity.healthcarecommission;


import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.gsszf.constant.Tables;
import io.swagger.annotations.ApiModelProperty;
import net.sf.oval.constraint.NotNull;

import java.math.BigInteger;

/**
 * 疫苗接种 实体类
 * @author xueyize
 * @since 2022-02-24
 */
@Table(name= Tables.YiMiaoJieZhong,comment = "疫苗接种")
public class YiMiaoJieZhong {

    @ApiModelProperty(value = "id", required = false)
    @NotNull
    @Column(name = "id", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "jiezhongdianname", required = false)
    @NotNull
    @Column(name = "jiezhongdianname", type = ColumnType.VARCHAR, length = 255, comment = "接种点名称")
    private String jieZhongDianName;

    @ApiModelProperty(value = "address", required = false)
    @NotNull
    @Column(name = "address", type = ColumnType.VARCHAR, length = 255, comment = "详细地址")
    private String address;

    @ApiModelProperty(value = "lxr", required = false)
    @NotNull
    @Column(name = "lxr", type = ColumnType.VARCHAR, length = 255, comment = "联系人")
    private String lxr;

    @ApiModelProperty(value = "tele", required = false)
    @NotNull
    @Column(name = "tele", type = ColumnType.VARCHAR, length = 255, comment = "联系电话")
    private String tele;

    @ApiModelProperty(value = "fuzedanwei", required = false)
    @NotNull
    @Column(name = "fuzedanwei", type = ColumnType.VARCHAR, length = 255, comment = "负责单位")
    private String fuZeDanWei;

    @ApiModelProperty(value = "jiezhongmenzhen", required = false)
    @NotNull
    @Column(name = "jiezhongmenzhen", type = ColumnType.VARCHAR, length = 255, comment = "接种门诊")
    private String jieZhongMenZhen;

    @ApiModelProperty(value = "jgbm", required = false)
    @NotNull
    @Column(name = "jgbm", type = ColumnType.VARCHAR, length = 255, comment = "机构编码")
    private String jgbm;

    @ApiModelProperty(value = "level", required = false)
    @NotNull
    @Column(name = "level", type = ColumnType.VARCHAR, length = 255, comment = "级别")
    private String level;

    @ApiModelProperty(value = "num", required = false)
    @NotNull
    @Column(name = "num", type = ColumnType.VARCHAR, length = 255, comment = "接种数目")
    private String num;

    @ApiModelProperty(value = "beizhu", required = false)
    @NotNull
    @Column(name = "beizhu", type = ColumnType.VARCHAR, length = 255, comment = "备注")
    private String beizhu;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getJieZhongDianName() {
        return jieZhongDianName;
    }

    public void setJieZhongDianName(String jieZhongDianName) {
        this.jieZhongDianName = jieZhongDianName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLxr() {
        return lxr;
    }

    public void setLxr(String lxr) {
        this.lxr = lxr;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getFuZeDanWei() {
        return fuZeDanWei;
    }

    public void setFuZeDanWei(String fuZeDanWei) {
        this.fuZeDanWei = fuZeDanWei;
    }

    public String getJieZhongMenZhen() {
        return jieZhongMenZhen;
    }

    public void setJieZhongMenZhen(String jieZhongMenZhen) {
        this.jieZhongMenZhen = jieZhongMenZhen;
    }

    public String getJgbm() {
        return jgbm;
    }

    public void setJgbm(String jgbm) {
        this.jgbm = jgbm;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }
}
