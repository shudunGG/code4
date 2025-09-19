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
 * 景区查询 实体类
 * @author xueyize
 * @since 2022-03-07
 */
@Table(name= Tables.ZY_JingQu,comment = "景区查询")
public class JingQu {

    @ApiModelProperty(value = "ID", required = false)
    @NotNull
    @Column(name = "ID", type = ColumnType.BIGINT, length = 20, comment = "id")
    @Id
    private BigInteger id;

    @ApiModelProperty(value = "name", required = false)
    @NotNull
    @Column(name = "name", type = ColumnType.VARCHAR, length = 255, comment = "景区名称")
    private String name;

    @ApiModelProperty(value = "level", required = false)
    @NotNull
    @Column(name = "level", type = ColumnType.VARCHAR, length = 255, comment = "等级")
    private String level;

    @ApiModelProperty(value = "address", required = false)
    @NotNull
    @Column(name = "address", type = ColumnType.VARCHAR, length = 255, comment = "地址")
    private String address;

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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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
}
