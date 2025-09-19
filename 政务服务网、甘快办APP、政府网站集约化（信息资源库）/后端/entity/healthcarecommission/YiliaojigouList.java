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
 * 医疗机构信息 实体类
 * @author xueyize
 * @since 2022-02-24
 */
@Table(name= Tables.YiliaojigouList,comment = "医疗机构信息")
public class YiliaojigouList {

    @ApiModelProperty(value = "id", required = false)
    @NotNull
    @Column(name = "id", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "name", required = false)
    @NotNull
    @Column(name = "name", type = ColumnType.VARCHAR, length = 255, comment = "机构名称")
    private String name;

    @ApiModelProperty(value = "address", required = false)
    @NotNull
    @Column(name = "address", type = ColumnType.VARCHAR, length = 255, comment = "详细地址")
    private String address;

    @ApiModelProperty(value = "tele", required = false)
    @NotNull
    @Column(name = "tele", type = ColumnType.VARCHAR, length = 255, comment = "联系电话")
    private String tele;

    @ApiModelProperty(value = "type", required = false)
    @NotNull
    @Column(name = "type", type = ColumnType.VARCHAR, length = 255, comment = "类型（1：三甲医院；2：血站；3：发热门诊；" +
            "4：急救机构；）")
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
