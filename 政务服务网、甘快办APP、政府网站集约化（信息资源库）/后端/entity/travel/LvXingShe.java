package com.hanweb.yyjr.zhangye.entity.travel;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.gsszf.constant.Tables;
import io.swagger.annotations.ApiModelProperty;
import net.sf.oval.constraint.NotNull;

import java.math.BigInteger;

/**
 * 旅行社 实体类
 * @author xueyize
 * @since 2022-03-07
 */
@Table(name= Tables.ZY_LvXingShe,comment = "旅行社")
public class LvXingShe {

    @ApiModelProperty(value = "ID", required = false)
    @NotNull
    @Column(name = "ID", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "name", required = false)
    @NotNull
    @Column(name = "name", type = ColumnType.VARCHAR, length = 255, comment = "旅行社名称")
    private String name;

    @ApiModelProperty(value = "socialcode", required = false)
    @NotNull
    @Column(name = "socialcode", type = ColumnType.VARCHAR, length = 255, comment = "社会代码")
    private String socialCode;

    @ApiModelProperty(value = "xukebianhao", required = false)
    @NotNull
    @Column(name = "xukebianhao", type = ColumnType.VARCHAR, length = 255, comment = "许可编号")
    private String xuKeBianHao;

    @ApiModelProperty(value = "address", required = false)
    @NotNull
    @Column(name = "address", type = ColumnType.VARCHAR, length = 255, comment = "场所地址")
    private String address;

    @ApiModelProperty(value = "service", required = false)
    @NotNull
    @Column(name = "service", type = ColumnType.VARCHAR, length = 255, comment = "经营业务")
    private String service;

    @ApiModelProperty(value = "tele", required = false)
    @NotNull
    @Column(name = "tele", type = ColumnType.VARCHAR, length = 255, comment = "联系电话")
    private String tele;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSocialCode() {
        return socialCode;
    }

    public void setSocialCode(String socialCode) {
        this.socialCode = socialCode;
    }

    public String getXuKeBianHao() {
        return xuKeBianHao;
    }

    public void setXuKeBianHao(String xuKeBianHao) {
        this.xuKeBianHao = xuKeBianHao;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }
}
